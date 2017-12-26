package model

trait CrudRepository[U, T <: Identifyable[U]] {

  def add(item: T)

  def update(item: T): Option[T]

  def remove(id: U): Option[T]

  def get(id: U): Option[T]
}
