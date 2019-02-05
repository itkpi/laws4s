package laws4cats

import cats.data.EitherK
import laws4s.boolean.Conjunction

import scala.language.higherKinds

trait conjunctions {
  implicit def eitherKConjunction[F[_], G[_]]: Conjunction.Aux[F, G, EitherK] = new Conjunction[F, G] {
    final type Out[Fx[_], Gx[_], Ax] = EitherK[Fx, Gx, Ax]

    def left[A](fa: F[A]): EitherK[F, G, A]  = EitherK.leftc[F, G, A](fa)
    def right[A](ga: G[A]): EitherK[F, G, A] = EitherK.rightc[F, G, A](ga)
  }
}
