package laws4s.boolean

import laws4s.{¬, Λ, V}

import scala.language.higherKinds

trait DeMorgan[F[_], G[_]] {
//  def firstLaw(x: ¬[F V G])(implicit `¬[F]`: ¬[F], `¬[G]`: ¬[G]): `¬[F]`.~ Λ `¬[G]`.~
//
//  def secondLaw(x: ¬[F Λ G])(implicit `¬[F]`: ¬[F], `¬[G]`: ¬[G]): `¬[F]`.~ V `¬[G]`.~
}

object DeMorgan
