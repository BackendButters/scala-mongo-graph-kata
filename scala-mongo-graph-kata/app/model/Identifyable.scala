package model

trait Identifyable[T] {

  def getId(): T
}
