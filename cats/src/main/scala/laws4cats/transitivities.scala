package laws4cats

import cats._
import cats.syntax.all._
import laws4s.relations.Transitivity
import scala.language.higherKinds

trait transitivities {
  final type Bind[F[_], A, B] = A => F[B]
  final type FunctionF[F[_], A, B] = F[A => B]

  implicit def bindTransitivity[F[_]: Monad]: Transitivity[Bind[F, ?, ?]] = new Transitivity[Bind[F, ?, ?]] {
    def apply[A, B, C](fab: A => F[B], fbc: B => F[C]): A => F[C] = { a =>
      fab(a).flatMap(fbc)
    }
  }
  implicit def apTransitivity[F[_]: Monad]: Transitivity[FunctionF[F, ?, ?]] =
    new Transitivity[FunctionF[F, ?, ?]] {
      def apply[A, B, C](fab: F[A => B], fbc: F[B => C]): F[A => C] = fab.flatMap { ab =>
        fbc.map { bc =>
          ab.andThen(bc)
        }
      }
    }
}
