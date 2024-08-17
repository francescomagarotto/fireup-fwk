package org.example;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.Getter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.example.configuration.FireUpModule;
import org.example.parser.BaseCommandLineParser;
import org.example.parser.CommandLineParser;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireUpApplicationBuilder {
    private String appName;
    private String[] args;
    private List<? extends Module> modules;
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

    public FireUpApplicationBuilder modules(List<? extends Module> modules) {
        this.modules = modules;
        return this;
    }

    public <I, O> EtlPipeline.EtlPipelineBuilder<I, O> etl() {
        return new EtlPipeline.EtlPipelineBuilder<>(this);
    }

    public void run() {
        Preconditions.checkNotNull(appName, "appName cannot be null");
        Preconditions.checkNotNull(etlPipeline, "ETL Pipeline logic cannot be null");
        this.modules = Objects.requireNonNullElse(modules, List.of(new FireUpModule(appName)));
        Injector injector = Guice.createInjector(modules);
        SparkSession sparkSession = injector.getInstance(SparkSession.class);
        Objects.requireNonNull(sparkSession, "Spark session cannot be null");
        CommandLineParser commandLineParser = new BaseCommandLineParser();
        this.etlPipeline.run(injector, commandLineParser.parse(args));
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

        void run(Injector injector, Map<String, String> args) {
            Dataset<I> sourceDataset = this.source.source(injector, args);
            Dataset<O> transformedDataset = this.logic.transform(sourceDataset, injector, args);
            this.sink.flush(transformedDataset, injector, args);
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

            public FireUpApplicationBuilder build() {
                Preconditions.checkNotNull(source, "ETL source cannot be null");
                Preconditions.checkNotNull(logic, "ETL logic cannot be null");
                Preconditions.checkNotNull(sink, "ETL sink cannot be null");
                this.fireUpApplicationBuilder.etlPipeline = new EtlPipeline<>(source, logic, sink);
                return fireUpApplicationBuilder;
            }
        }
    }

}
