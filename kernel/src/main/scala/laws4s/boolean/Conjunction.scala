package laws4s.boolean
import scala.language.higherKinds

trait Conjunction[F[_], G[_]] {
  type Out[_[_], _[_], _]

  def left[A](fa: F[A]): Out[F, G, A]
  def right[A](ga: G[A]): Out[F, G, A]
}

object Conjunction {
  type Aux[F[_], G[_], Out0[_[_], _[_], _]] = Conjunction[F, G] { type Out[Fx[_], Gx[_], a] = Out0[Fx, Gx, a] }
}
