package co.datadome.pub.scalabenchmarks.jvms.libs.cats

import cats.{Applicative, Foldable, Functor, Monad, Monoid}

import scala.annotation.tailrec

sealed trait Ternary[+A]

object Ternary {
  case object Zero extends Ternary[Nothing]

  final case class One[A](value: A) extends Ternary[A]

  case object Many extends Ternary[Nothing]

  given [A]: Monoid[Ternary[A]] with {
    def empty: Ternary[A] = Zero

    def combine(x: Ternary[A], y: Ternary[A]): Ternary[A] = (x, y) match {
      case (Zero, other) => other
      case (other, Zero) => other
      case (Many, _) => Many
      case (_, Many) => Many
      case (One(_), One(_)) => Many
    }
  }

  given Functor[Ternary] with {
    def map[A, B](fa: Ternary[A])(f: A => B): Ternary[B] = fa match {
      case Zero => Zero
      case Many => Many
      case One(value) => One(f(value))
    }
  }

  given Applicative[Ternary] with {
    def pure[A](x: A): Ternary[A] = One(x)

    def ap[A, B](ff: Ternary[A => B])(fa: Ternary[A]): Ternary[B] = (ff, fa) match {
      case (Zero, _) => Zero
      case (_, Zero) => Zero
      case (Many, _) => Many
      case (_, Many) => Many
      case (One(f), One(a)) => One(f(a))
    }
  }

  given Monad[Ternary] with {
    def pure[A](x: A): Ternary[A] = One(x)

    def flatMap[A, B](fa: Ternary[A])(f: A => Ternary[B]): Ternary[B] = fa match {
      case Zero => Zero
      case Many => Many
      case One(value) => f(value)
    }

    @tailrec
    final def tailRecM[A, B](a: A)(f: A => Ternary[Either[A, B]]): Ternary[B] = f(a) match {
      case Zero => Zero
      case Many => Many
      case One(Left(nextA)) => tailRecM(nextA)(f)
      case One(Right(b)) => One(b)
    }
  }

  given Foldable[Ternary] with {
    def foldLeft[A, B](fa: Ternary[A], b: B)(f: (B, A) => B): B = fa match {
      case Zero => b
      case Many => b
      case One(value) => f(b, value)
    }

    def foldRight[A, B](fa: Ternary[A], lb: cats.Eval[B])(f: (A, cats.Eval[B]) => cats.Eval[B]): cats.Eval[B] = fa match {
      case Zero => lb
      case Many => lb
      case One(value) => f(value, lb)
    }
  }

}