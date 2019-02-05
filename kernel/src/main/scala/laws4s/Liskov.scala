package laws4s

import scala.language.higherKinds
import laws4s.relations.Transitivity

trait Liskov[A, B] {
  def apply(a: A): B
}

trait LowPriorityLiskov {
  implicit def liskovT[F[_], A, B](implicit ev: Liskov[A, B]): Liskov[F[A], F[B]] = new Liskov[F[A], F[B]] {
    def apply(a: F[A]): F[B] = a.asInstanceOf[F[B]]
  }
  implicit def transitivelyLiskov[A, B, C](implicit ev: Liskov[A, B], ev2: Liskov[B, C]): Liskov[A, C] = transitively[Liskov, A, B, C]
}

object Liskov extends LowPriorityLiskov {
  def apply[A, B](implicit ev: Liskov[A, B]) = ev

  implicit def fromScala[A, B](implicit ev: A <:< B): Liskov[A, B] = new Liskov[A, B] {
    def apply(a: A): B = ev(a)
  }

  implicit def liskovTransitivity: Transitivity[Liskov] = new Transitivity[Liskov] {
    def apply[A, B, C](fab: Liskov[A, B], fbc: Liskov[B, C]): Liskov[A, C] = new Liskov[A, C] {
      def apply(a: A): C = fbc(fab(a))
    }
  }
}
