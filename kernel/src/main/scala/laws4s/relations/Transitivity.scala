package laws4s.relations

import scala.language.higherKinds

trait Transitivity[F[_, _]] {
  def apply[A, B, C](fab: F[A, B], fbc: F[B, C]): F[A, C]
}

trait LowPriorityTransitivity {
  implicit def functionTransitivity: Transitivity[Function1] = new Transitivity[Function] {
    def apply[A, B, C](fab: Function[A, B], fbc: Function[B, C]): Function[A, C] =
      fab.andThen(fbc)
  }
}

object Transitivity extends LowPriorityTransitivity
