import laws4s.boolean.{Conjunction, Disjunction, LowPriorityStuff}
import laws4s.relations.{Commutativity, CommutativityK, Transitivity}

import scala.language.higherKinds

package object laws4s extends LowPriorityStuff {
  implicit class CommutativeOps[F[_, _], A, B](private val fab: F[A, B]) extends AnyVal {
    def swap(implicit ev: Commutativity[F]): F[B, A] = ev(fab)
  }

  implicit class TransitivityOps[F[_, _], A, B](private val fab: F[A, B]) extends AnyVal {
    def andThen[C](fbc: F[B, C])(implicit ev: Transitivity[F]): F[A, C] = ev(fab, fbc)
  }

  implicit class JunctionSyntax[F[_], A](private val self: F[A]) extends AnyVal {
    def asLeft[G[_]](implicit conjunction: Conjunction[F, G]): conjunction.Out[F, G, A]  = conjunction.left(self)
    def asRight[G[_]](implicit conjunction: Conjunction[G, F]): conjunction.Out[G, F, A] = conjunction.right(self)
  }

  implicit class JunctionSyntax2[F[_[_]], T[_]](private val self: F[T]) extends AnyVal {
    def and[G[_[_]]](that: G[T])(implicit disjunction: Disjunction[F, G]): disjunction.Out[T] = disjunction(self, that)
  }

  implicit class CommutativityKSyntax[F[_[_], _[_]], Fx[_], Gx[_]](private val self: F[Fx, Gx]) extends AnyVal {
    def swap(implicit commutativityK: CommutativityK[F]): F[Gx, Fx] = commutativityK(self)
  }

  implicit class LiskovSyntax[A](private val self: A) extends AnyVal {
    def substitute[B](implicit ev: Liskov[A, B]): B = ev(self)
  }
}
