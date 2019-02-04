package laws4s

import scala.language.higherKinds
import laws4s.relations.Transitivity

trait Liskov[A, B] {
  def apply(a: A): B
}

trait LowPriorityLiskov {
  implicit def fromScala[A, B](implicit ev: A <:< B): Liskov[A, B] = new Liskov[A, B] {
    def apply(a: A): B = ev(a)
  }
}

object Liskov extends LowPriorityLiskov {
  implicit def liskovTransitivity: Transitivity[Liskov] = new Transitivity[Liskov] {
    def apply[A, B, C](fab: Liskov[A, B], fbc: Liskov[B, C]): Liskov[A, C] = new Liskov[A, C] {
      def apply(a: A): C = fbc(fab(a))
    }
  }
}
