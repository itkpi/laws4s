package laws4cats

import cats._
import cats.data.EitherT
import laws4s.relations.Commutativity
import scala.language.higherKinds

trait commutativities {
  implicit def eitherTCommutativity[F[_]: Functor]: Commutativity[EitherT[F, ?, ?]] = new Commutativity[EitherT[F, ?, ?]] {
    def apply[A, B](fab: EitherT[F, A, B]): EitherT[F, B, A] = fab.swap
  }
}
