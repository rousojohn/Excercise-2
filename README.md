Excercise-2
===========
Search Algorithm element in a table
The classical sequential binary search algorithm can be generalized so that at each stage of the algorithm is performed to compare the input element with q-1 elements instead of one (the middle point ) . Specifically if we seek the point x between the elements of a matrix A = [ a1 , a2 , ..., AN ] , we can compare the first x with the elements AN / q, a2n / q, a3n / q, ..., a ( q-1) ∙ n / q. If x is not found among these data , we determine the time ( aj ∙ n / q +1 ... a (j +1) ∙ n/q-1) which belongs to the x and then the above search is performed recursively in this space
