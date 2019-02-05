package com.example

import cats._
import cats.implicits._
import laws4cats._
import laws4s._
import laws4s.relations.Commutativity
import scala.language.higherKinds

object Main {
  sealed trait Foo
  sealed trait Bar extends Foo
  sealed trait Baz extends Bar {
    override def toString: String = "Baz"
  }

  def main(args: Array[String]): Unit = {
    println(abstarctOverBiFunctor(1 -> 2.0)) // derives `swap` for arbitrary commutative bifunctor

    println(transitiveImplicitConversions[Baz, Bar, Foo](new Baz {})) // derives transitive implicit conversions

    val eitherMonad: Monad[Either[String, ?]] =
      Applicative[Either[String, ?]] and FlatMap[Either[String, ?]] // derives type-classes based on inheritance rules

    println(eitherMonad.flatMap(Right[String, String]("1")) { x =>
      scala.util.Try(x.toInt).toEither.left.map(_.getMessage)
    })
  }

  private def abstarctOverBiFunctor[F[_, _]: Commutativity, A, B](fab: F[A, B])(implicit F: Monad[F[A, ?]]): F[String, A] =
    fab.map(_.toString).swap

  private def transitiveImplicitConversions[A, B, C](a: A)(implicit ev: Liskov[A, B], ev2: Liskov[B, C]): C =
    a.substitute[C]

}
