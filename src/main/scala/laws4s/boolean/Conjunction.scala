package laws4s.boolean

import scala.language.higherKinds

trait Conjunction[F[_], G[_]] {
  type Out[_]

  def apply[A](fa: F[A], ga: G[A]): Out[A]
}

object Conjunction {
  type Aux[F[_], G[_], Out0[_]] = Conjunction[F, G] { type Out[x] = Out0[x] }
  type Naive[F[_], G[_]]        = Conjunction[F, G] { type Out[x] = F[x] with G[x] }
}
