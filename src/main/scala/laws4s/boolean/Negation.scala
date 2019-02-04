package laws4s.boolean

import scala.language.higherKinds

trait Negation[F[_]] {
  type Out[_]
  final type ~[x] = Out[x]

  def negate[A](fa: F[A]): Out[A]
}

object Negation {
  type Aux[F[_], Out0[_]] = Negation[F] { type Out[x] = Out0[x] }
}
