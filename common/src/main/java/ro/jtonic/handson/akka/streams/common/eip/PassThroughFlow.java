package ro.jtonic.handson.akka.streams.common.eip;

import akka.NotUsed;
import akka.japi.function.Function2;
import akka.japi.Pair;
import akka.stream.FanInShape2;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.Broadcast;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.GraphDSL;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.ZipWith;

public class PassThroughFlow {

  public static <A, T> Graph<FlowShape<A, Pair<T, A>>, NotUsed> create(Flow<A, T, NotUsed> flow) {
    return create(flow, Keep.both());
  }

  public static <A, T, O> Graph<FlowShape<A, O>, NotUsed> create(
      Flow<A, T, NotUsed> flow, Function2<T, A, O> output) {
    return Flow.fromGraph(
        GraphDSL.create(
            builder -> {
              UniformFanOutShape<A, A> broadcast = builder.add(Broadcast.create(2));
              FanInShape2<T, A, O> zip = builder.add(ZipWith.create(output));
              builder.from(broadcast.out(0)).via(builder.add(flow)).toInlet(zip.in0());
              builder.from(broadcast.out(1)).toInlet(zip.in1());
              return FlowShape.apply(broadcast.in(), zip.out());
            }));
  }
}