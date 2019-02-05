package laws4cats

import scala.language.higherKinds
import cats.{Applicative, FlatMap, Monad}
import laws4s.boolean.Disjunction

trait disjunctions {
  implicit val applicativeFlatMapDisjunction: Disjunction.Aux[Applicative, FlatMap, Monad] = new Disjunction[Applicative, FlatMap] {
    type Out[T[_]] = Monad[T]
    def apply[T[_]](fa: Applicative[T], ga: FlatMap[T]): Monad[T] = new Monad[T] {
      def pure[A](x: A): T[A]                                 = fa.pure(x)
      def flatMap[A, B](fa: T[A])(f: A => T[B]): T[B]         = ga.flatMap(fa)(f)
      def tailRecM[A, B](a: A)(f: A => T[Either[A, B]]): T[B] = ga.tailRecM(a)(f)
    }
  }
}
