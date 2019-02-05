package laws4s.relations

import scala.language.higherKinds

trait Commutativity[F[_, _]] {
  def apply[A, B](fab: F[A, B]): F[B, A]
}

trait LowPriorityCommutativity {
  implicit val eitherCommutativity: Commutativity[Either] = new Commutativity[Either] {
    def apply[A, B](fab: Either[A, B]): Either[B, A] = fab.swap
  }
  implicit val tupleCommutativity: Commutativity[Tuple2] = new Commutativity[Tuple2] { def apply[A, B](fab: (A, B)): (B, A) = fab.swap }
}

object Commutativity extends LowPriorityCommutativity
