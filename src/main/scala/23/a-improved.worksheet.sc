import scala.collection.mutable

// Define a case class for the node
class Node(var value: Int, var left: Node = null, var right: Node = null)

// Define the circular list structure with a HashMap
class CircularList(numbers: List[Int]) {
  var head: Node = _

  // HashMap to store number -> node references
  val nodeMap: mutable.HashMap[Int, Node] = mutable.HashMap()

  // Function to populate the circular list with the given numbers
  def populate(): Unit = {
    if (numbers.isEmpty) return

    // Create the first node (head)
    head = Node(numbers.head)
    nodeMap += (numbers.head -> head) // Add to HashMap
    var current = head

    // Iterate over the remaining numbers
    for (num <- numbers.tail) {
      val newNode = Node(num)
      current.right = newNode
      newNode.left = current
      current = newNode

      // Add the new node to the HashMap
      nodeMap += (num -> newNode)
    }

    // Complete the circular structure
    current.right = head // Last node points to the head
    head.left = current // Head points back to the last node
  }

  // Function to get the node reference by its value
  def getNode(value: Int): Option[Node] = nodeMap.get(value)

  // Method to move the next three nodes from one node to another node's right
  def moveNextThreeNodes(from: Node, to: Node): Unit = {
    // Extract the next three nodes from 'from'
    val firstOfThree = from.right
    val secondOfThree = firstOfThree.right
    val thirdOfThree = secondOfThree.right
    val afterThird = thirdOfThree.right

    // Detach the three nodes from their original position
    from.right = afterThird
    afterThird.left = from

    // Insert the three nodes to the right of 'to'
    val toRight = to.right
    to.right = firstOfThree
    firstOfThree.left = to

    thirdOfThree.right = toRight
    toRight.left = thirdOfThree
  }

  def nextNumber(from: Node): Int = {
    val firstOfThree = from.right
    val secondOfThree = firstOfThree.right
    val thirdOfThree = secondOfThree.right
    val afterThird = thirdOfThree.right

    var current = from.value
    current = if current == 1 then 9 else current - 1
    while (
      current == firstOfThree.value || current == secondOfThree.value || current == thirdOfThree.value
    ) {
      current = if current == 1 then 9 else current - 1
    }
    current
  }

  def depth(node: Node, n: Int): List[Int] = n match {
    case 0 => Nil
    case _ => node.value :: depth(node.right, n - 1)
  }
}

// Numbers to populate the circular list
val numbers = List(7,1,2,6,4,3,5,8,9)

// Create and populate the circular list
val circularList = new CircularList(numbers)
circularList.populate()

var fromNode =
  circularList
    .getNode(numbers(0))
    .get // Move the 3 nodes to the right of node with value 6

for (_ <- 1 to 100) do
  val next = circularList.nextNumber(fromNode)
  val toNode = circularList.getNode(next).get
  circularList.moveNextThreeNodes(fromNode, toNode)
  fromNode = fromNode.right

circularList.depth(circularList.getNode(1).get, 9)
