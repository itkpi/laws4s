import laws4s.boolean.{Conjunction, Disjunction, Negation}

import scala.language.higherKinds

package object laws4s {
  type ¬[F[_]] = Negation[F]

  type Λ[F[_], G[_]] = Conjunction[F, G]
  val Λ = Conjunction

  type V[F[_], G[_]] = Disjunction[F, G]
  val V = Disjunction

  type <:<[A, B] = Liskov[A, B]
  val <:< = Liskov
}
