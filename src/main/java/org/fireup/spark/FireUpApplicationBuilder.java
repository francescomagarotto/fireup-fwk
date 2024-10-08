package org.fireup.spark;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.fireup.spark.configuration.ConfigurationModule;
import org.fireup.spark.configuration.FireUpModule;
import org.fireup.spark.etl.Logic;
import org.fireup.spark.etl.Sink;
import org.fireup.spark.etl.Source;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireUpApplicationBuilder {
    private String appName;
    private String[] args;
    private List<AbstractModule> modules = new ArrayList<>(0);
    private EtlPipeline<?, ?> etlPipeline;

    public static FireUpApplicationBuilder create() {
        return new FireUpApplicationBuilder();
    }

    public FireUpApplicationBuilder args(String[] args) {
        this.args = args;
        return this;
    }

    public FireUpApplicationBuilder appName(String appName) {
        this.appName = appName;
        return this;
    }

    public FireUpApplicationBuilder withAdditionalModules(List<AbstractModule> modules) {
        this.modules = modules;
        return this;
    }

    public <I, O> EtlPipeline.EtlPipelineBuilder<I, O> etl() {
        return new EtlPipeline.EtlPipelineBuilder<>(this);
    }

    public void run() {
        Preconditions.checkNotNull(appName, "appName cannot be null");
        Preconditions.checkNotNull(etlPipeline, "ETL Pipeline logic cannot be null");
        List<AbstractModule> defaultModule = List.of(new ConfigurationModule(this.args), new FireUpModule(appName));
        CollectionUtils.addAll(modules, defaultModule);
        Injector injector = Guice.createInjector(modules);
        SparkSession sparkSession = injector.getInstance(SparkSession.class);
        Objects.requireNonNull(sparkSession, "Spark session cannot be null");
        Config config = injector.getInstance(Config.class);
        this.etlPipeline.run(injector, sparkSession, config);
        sparkSession.close();
    }


    @Getter
    public static class EtlPipeline<I, O> {
        private final Source<I> source;
        private final Logic<I, O> logic;
        private final Sink<O> sink;

        private EtlPipeline(Source<I> source, Logic<I, O> logic, Sink<O> sink) {
            this.source = source;
            this.logic = logic;
            this.sink = sink;
        }

        void run(Injector injector, SparkSession sparkSession, Config config) {
            Dataset<I> sourceDataset = this.source.source(injector, sparkSession, config);
            Dataset<O> transformedDataset = this.logic.logic(sourceDataset, sparkSession, injector, config);
            this.sink.flush(transformedDataset, injector, sparkSession, config);
        }

        public static class EtlPipelineBuilder<I, O> {
            private final FireUpApplicationBuilder fireUpApplicationBuilder;
            private Source<I> source;
            private Logic<I, O> logic;
            private Sink<O> sink;

            public EtlPipelineBuilder(FireUpApplicationBuilder fireUpApplicationBuilder) {
                this.fireUpApplicationBuilder = fireUpApplicationBuilder;
            }

            public EtlPipeline.EtlPipelineBuilder<I, O> source(Source<I> source) {
                this.source = source;
                return this;
            }

            public EtlPipeline.EtlPipelineBuilder<I, O> logic(@NotNull Logic<I, O> logic) {
                this.logic = logic;
                return this;
            }

            public EtlPipeline.EtlPipelineBuilder<I, O> sink(@NotNull Sink<O> sink) {
                this.sink = sink;
                return this;
            }

            public FireUpApplicationBuilder toApplication() {
                Preconditions.checkNotNull(source, "ETL source cannot be null");
                Preconditions.checkNotNull(logic, "ETL logic cannot be null");
                Preconditions.checkNotNull(sink, "ETL sink cannot be null");
                this.fireUpApplicationBuilder.etlPipeline = new EtlPipeline<>(source, logic, sink);
                return fireUpApplicationBuilder;
            }
        }
    }

}
