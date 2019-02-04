package laws4s.boolean

import scala.language.higherKinds

trait Disjunction[F[_], G[_]] {
  def left[A](fa: F[A]): F[A] Either G[A]

  def right[A](ga: G[A]): F[A] Either G[A]
}

trait LowPriorityDisjunction {
  implicit def instance[F[_], G[_]]: Disjunction[F, G] = new Disjunction[F, G] {
    def left[A](fa: F[A]): Either[F[A], G[A]] = Left(fa)

    def right[A](ga: G[A]): Either[F[A], G[A]] = Right(ga)
  }
}

object Disjunction extends LowPriorityDisjunction
