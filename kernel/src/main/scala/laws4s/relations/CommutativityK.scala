package laws4s.relations

import scala.language.higherKinds

trait CommutativityK[F[_[_], _[_]]] {
  def apply[Fx[_], Gx[_]](x: F[Fx, Gx]): F[Gx, Fx]
}

trait CommutativityK2[F[_[_[_]], _[_[_]]]] {
  def apply[Fx[_[_]], Gx[_[_]]](x: F[Fx, Gx]): F[Gx, Fx]
}