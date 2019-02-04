package laws4s.relations

import scala.language.higherKinds

trait Commutativity[F[_, _]] {
  def apply[A, B](fab: F[A, B]): F[B, A]
}

trait LowPriorityCommutativity {
  implicit def eitherCommutativity: Commutativity[Either] = new Commutativity[Either] {
    def apply[A, B](fab: Either[A, B]): Either[B, A] = fab.swap
  }
}

object Commutativity extends LowPriorityCommutativity
