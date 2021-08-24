
/*@ predicate sorted(int *t,integer i,integer j) =
  @   \forall integer k, integer l; i <= k < l <= j ==> t[k] <= t[l];
  @*/


/*@ requires N>=1 && \valid(A+(0..N-1)); 
  @ assigns A[0..N-1]; 
  @ ensures sorted(A,0,N-1); 
  @*/
void selectionSort(int A[], int N) {
    int i, j, tmp, min;
    
    /*@ loop invariant 0<=j<= N ;
      @ loop invariant sorted(A,0,j);
      @ loop assigns j, tmp, min, i, A[0..(N-1)];
      @ loop variant N-j; 
      @*/
    for (j=0 ; j<N-1 ; j++) {
        min = j;
    
        /*@ loop invariant j+1 <= i <= N && j <= min <= i ;
	        @ loop invariant
          @    (\forall integer a;
          @           j <= a < i ==> min <= a);
          @ loop assigns i, min;
          @ loop variant N-i; 
          @*/
        for (i = j+1; i<N; i++)
            if (A[i] < A[min])
                min = i;

        tmp = A[min];
        A[min] = A[j];
        A[j] = tmp;
    }
}
