package laws4s.boolean

import laws4s.relations.Transitivity
import scala.language.higherKinds

trait LowPriorityStuff {
  def transitively[F[_, _], A, B, C](implicit ev: F[A, B], ev2: F[B, C], transitivity: Transitivity[F]): F[A, C] =
    transitivity(ev, ev2)
}
