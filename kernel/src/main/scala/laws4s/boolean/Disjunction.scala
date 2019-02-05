package laws4s.boolean

import laws4s.relations.{CommutativityK, CommutativityK2}

import scala.language.experimental.macros
import scala.language.higherKinds

trait Disjunction[F[_[_]], G[_[_]]] {
  type Out[_[_]]

  def apply[T[_]](fa: F[T], ga: G[T]): Out[T]
}

object Disjunction {
  type Aux[F[_[_]], G[_[_]], Out0[_[_]]] = Disjunction[F, G] { type Out[T[_]] = Out0[T] }

  implicit val disjunctionCommutativity: CommutativityK2[Disjunction] = new CommutativityK2[Disjunction] {
    def apply[Fx[_[_]], Gx[_[_]]](x: Disjunction[Fx, Gx]): Disjunction.Aux[Gx, Fx, x.Out] = new Disjunction[Gx, Fx] {
      type Out[T[_]] = x.Out[T]

      def apply[T[_]](fa: Gx[T], ga: Fx[T]): Out[T] = x(ga, fa)
    }
  }
}
