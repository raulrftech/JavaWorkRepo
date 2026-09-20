package Day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class day5 {
    public static void main(String[] args) {
        int[] variableUse = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] testArr2 = {8, 2, 15, 3, 9, 1, 20, 4, 6, 11, 2, 18, 5, 7, 3, 13, 9, 1, 16, 4};
        int[] testArr = {1, 2, 2, 3, 1, 4, 4, 4, 5, 5, 2, 2, 2, 3, 3};
        int[] tA3 = new int[] {1, 2, 3, 4, 5, 4, 3};
        System.out.println(findShortestSub(variableUse, 12));
    }

    // Big-O Speed
    // The core idea first:
    //      Big-O and its relatives describe how an algorithm's work gorws as input size n grows - not wall-clock seconds which depend on hardware but the rate at which the number of operations increases 
    //          it describes behavior as n gets large, not the exact count for any one n
    // Big-O(O) - upper bound
    //      Guarantees the algorithm never does mroe than a constant multiple of htis, once n is large enough
    //      How to Determine Big-O (upper bound/worst case):
    //          Step 1:
    //              Find every loop and recursive call.
    //              For each one, ask "how many times can this run, at most, as a function of n"
    //          Step 2:
    //              For nested loops, multiply thier individual counts together but only if the inner loops count doesnt shrink relative to the counter
    //                  If it does shrink, you need the actual sum, not a naive multiplication
    //          Step 3:
    //              For sequential (not lested loops), add their counts, then keep only the largest term - O(n) + 0(n^2) simplifies to O(n^2), since smaller term becomes irrelevant
    //          Step 4:
    //              Check every operation inside each loop iteration
    //                  if any single "step" of a loop is itself doing hidden work (like calling .contains() on a list, which is its own loop internally)
    //                      that hidden cost multiplies into the total.
    //                  Ask yourself - is this single line actually O(1) or does it have its own loop buried inside it
    //          Step 5:
    //              Whatever the largest surviving term is, after dropping constants and lower-order terms, is the Big )
    //      The formal definition:
    //          f(n) is O(g(n)) if there exist positive constants c and n0 such that f(n) <= c * g(n) for all n >= n0
    //          In plain terms, once n is large enough, f(n) never exceeds some constant multiple of g(n)
    //              This is why constants get dropped: f(n) = 3n+5 is O(n), because you can always find some constant c like c=4 once n >= 5 that keeps 3n+5<=4n tru forever after
    //              The constant doesnt matter to the classification - only the shape of growth does
    //      A genuinely useful, practical skill: the common complexity classes, ordered, so you can recognize where a new alg lands relative to known ones
    //          From best to worst: O(1) constant -> O(logn) logarithmic(binary search)->O(n) linear->O(nlogn) linearithmic(efficient sorting)->O(n^2)quadratic (nested loops over the same data)-> O(2^n) exponential(naive recursive Fibonacci, brute-force subsets)-> O(n!) factorial (brute-force permuations)
    //          Knowing this ladder cold means you can instantly sense "this feels like it should be O(nlogn) but the code smells like O(2^n)"
    // Omega - lower bound, the mirror image
    //      guarantees it never does less
    //      How to Determine Big-Omega:
    //          Mirror Question: Wht is the minimum amount of work this alg is structurally forced to do, no matter what the input looks like
    //          Concretely-trace through the code and ask
    //              Is there any way for this alg to exit early, skip work, or finish faster depending on what values are inside the input?
    //                  If no early exit exists anywhere then Omega equals to Big) exactly
    //                      the alg has a single, tight bound
    //                  If an early exit exists
    //                      then Omega is whatever the fastest possible triggering scenario looks like - often O(1) if the very first element checked could satisfy exit condition
    // Big-Theta(theta)- a tight bound
    //      meaning the upper and lower bounds coincide; the algorithm's actual behavior, not just a ceiling or floor
    //      f(n) is Theta(g(n)) if it is both O(g(n)) and Omega(g(n)) simultaneously - meaning g(n) sandwiches f(n) from above and below, up to constant factors
    //      Concretely: f(n)=3n+5 is Theta(n) because you can find constant c1,c2 such that c1 * n <= 3n+5 <= c2 * n for large enough n
    //      Theta is the honest, complete discription of an algorithms growth when its best and owrst case genuinely coincide
    //          which is why a tight loop with no early exit is properly described as Theta(n^2) not just o(n^2)
    //              saying only O(n^2) is technically true but incomplete, since O(n^2) alone doesnt rule out the algorithm secretly being capable of running in O(n) sometimes
    //      Theta is the stronger, more informative claim whenever it genuinely applies
    // Worth a real correction to something used loosely all along
    //      HashMap's.get() is O(1) is actually informally standing in for Theta(1) average case - 
    //          best, worst, and average all land at the same order under normal conditions
    //      Linear search is the case where they genuinely diverge:
    //          best case is Omega(1), the itmes first, one check, worst case is O(n) the itmes last or absent, every element checked
    //              and since best and worst dotn't match, there's no single Theta describing the whole algorithm, only "the worst case is Theta(n), the best case is Theta(1)", stated separately
    // Time and space, always together, and space split precisely:
    //      time complexity counts operations; space complexity counts additional memory
    //      Space splits further into auxiliary space (extra memory the lagorithm itself allocates) vs input space (memory the input already occupied before the algorithm touched it)
    //          only auxiliary space usually counts against the algorithm's own footprint, since the input existed regardless
    //              How to Determine Aux Space:
    //                  Step 1:
    //                      identify every variable, array list, or object the alg creates iself, separate from whatever was already passed in as input
    //                  Step 2:
    //                      For each one, ask "does its size stay fixed regardless of n, or does it grow as n grows"
    //                      A single int/boolean/ref var is always O(1), no matter how large the input is, since its always exactly one variable
    //                      A new array/list/set that could hold up to n items is O(n)
    //                  Step 3:
    //                      If the alg is recursive, the call stack itself counts as space
    //                          each active recursive call sitting on the stack is real memory, so recursion depth directly contributes to aux space
    // Amortized analysis - the ArrayList mechanism traced with real numbers
    //      Doubling capacity each time it fills:
    //          insert 1 (fits, cost 1), insert 2(full, resize to 2, copy 1 + insert = cost 2), insert 3(full, resize to 4, copy 2 + insert = cost 3), inserts 4 (fits, cost 1), insert 5(full, resize to 8, copy 4 + insert = cost 5), inserts 6-8(fit, cost 1 each), insert 9(full, resize to 16, copy 8 + insert = cost 9)
    //          Total cost after 9 insertions: 24 - average 2.67 per insertion, a small constant, not growing with n
    //          That is the actual proof behind "amortized O(1)": individual operations vary wildly, but the average, taken over many operations, stays bounded
    //          Java's real ArrayList gorws by 1.5x not 2x but the same math holds under any constnt growth factor greater than 1
    //      This analysis answers "even though individual operations vary wildly in cost, what does the cost work out to per operation, averages over a long sequence"
    //      The formal technique most commmonly used is the aggregate method
    //          Run the sequence of n operations, sum their total cost, then divide by n to get the amortized cost per operation
    //      After n insertions, the total cost of all resizing-copies ever done is 1+2+4+8...=2n(a geometric series, which sums to roughly double the final size, standard result worth knowing)
    //      Add the n insertions themselves, total work is n + 2n = 3n then divide by n operations -> 3, a constant, meaning no matter how large n grows
    //          the avg cost per insertions stays at a fixed number, never scaling up
    //      That constant per operation result, even though individual operations occasionally spike to O(n) (the resize itself) is precisely what amortized O(1) formally means
    // Best/worst/average, why the distinction is practically load-bearing, not academic
    //      Quciksort (coming in Phase 9) has an O(n^2) worst case with unlucky pivot choices but an average case of O(nlogn) - which is precisely why it's used constantly in practice despite that bad worst case;
    //          average behavior, not worst-case paranoia, drives most real tool choice

    // Big-O Space
    // Space complexity - the same three notations, applied to memory instead of operations
    // Everything about O, Omega and Theta works identically in structure
    //      Theyre still describing gorwth rate as a function of n, just counting bytes of additional memory instead of operations performed
    //      O(g(n)) for space means: THis alg's memory usage never exceeds a constant multile of g(n), for large enough n
    //          same formal, c, n0 definiton as avoce
    // The creitical split, worth being axact about: total space vs aux space
    //      Total space includes the input itself plus whatever the alg additionally creates
    //      Aux space is the number that actually matters almost every time youre asked for space complexity
    //          is only what the alg creates beyond the input
    // What actually counts as auxiliary space, itemized precisely
    //      Any new array, list, map or set the alg creates - sized relative to n if it can grow to hold up to n items
    //      Any fixed numer of indiv vars always are O(1), regardless of how large n is, since count of variables doesnt change with input size, only their vals do
    // Recursions call stack
    //      Everytime a method calls itself, a new stack frame is pushed onto the call stack - a real chunk of memory holding that call's local variables and where to return once finished
    //      If a recursive method call itself n times deep before hitting a base case, thats n stack frames sitting in memory simultaneously at the deepest point
    //          Meaning recursion depth directly contributes O(n) aux space, even if the method never creates a single array or list
    // Omega and Theta for Space, same logic as for time
    //      Omega(g(n)) is minimum memory the alg is guaranteed to use across any input of its structure
    // Examples of Classifications
        // O(1) Space -
        //      method using fixed, small number of vars regardless of input size.
        // O(n) Space -
        //      mthod whos memory usage grows proportionally with input size
        // Omega(1) Space - 
        //      minimum memory some alg's structure could every use, across any input
        //      method that sometimes returns early with no allocation, and sometimes allocates depending on input would have Omega(1)
        // Omega(n) Space -
        //      minimum memory guaranteed no matter what, because the structure itself forces it
        // Theta(n) Space -
        //      reched whenever O(n) and Omega(n) coincide, meaning alg always uses space proportional to n, with no variance based on input content

    // Exercise 1
    // Four plain funcs, for each, state:
    //      time complexity Big O and Big Omega seperately if best and worst case genuinely diverge
    //      Auxiliary space complexity, whether it mutates in place or allocates new memory and whther it could be improved, to what complexity if so
    // Func 1
    //    In this example, Big O is n^2, theres a nested for loop that increments the counter for every index encountered, itll run like this
    //          size of array -> sizeOf = 1 and so forth, i.e. 10+9+8+7...which would be 55
    //    Since there is no early exit, Omega would be equal to Big O so n^2
    //    I need more clarification for Theta but isnce it's a tight loop itll be the same as Omega and O, n^2
    //    I need help with this aux space complexity like a better explanation since theres only an input array that takes up space and the count var
    //    I believe that the minimum amount that this alg would do, in other words the best case for Omega would be you know what it would be the same because it implements two loops
    //    But the alg has a whole can be written with one for loop for (i = arr.length; i >= 0; i--) then adding that to the counter
    //    Regarding the auxiliary space dilemma:
    //      The input array arr doesnt count toward auxiliary space at all since it existed before the method ever ran, its not something the alg itself created
    //      The only thing this method allocates is count, a single int, one fixed size variable regardless of whether arr has 10 elements or 10 million
    //      This is O(1) auxiliary space. Theres no scenario here where memory usages grows with n. So aux space is 1 the simplicity itself is the answer, since 1 var is created
    public static int sumTriangular(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) { count++;}
        } return count;
    }
    public static int sumTriangular_Improvised(int[] arr) {
        int count = 0;
        for (int i = arr.length; i >= 0; i--) {
            count += i;
        } return count;
    }
    // Func 2
    //   Since there is a for loop containing an if statement calling .contains on the AL, that would be n^2
    //   Since the return provides an early exit, and considering that the AL seen is created before the for loop is ran that guarantees that there is no way that
    //      the first element in the parameter would be in this AL so that might be the 2nd integer in the parameter in this case it then it is Omega(n)
    //   Since O and Omega dont coincide, i dont think there is a theta
    //   Aux space is O(n) since the AL created grows as each nth index is encountered, if the early exit isnt hit
    //   Since this method returns true once the first dupe is found it returns true but might not have had ran through the whole input list
    //      so instead of returning true and not returning the AL, we can implement a hashmap so that the lookup is direct O(1)
    // Correction on the Omega reasoning
    //      Omega actually asks for what is the absolute best case input that produces the fastest possible run, and how fast is that
    //          So consider input of [5,5] and trace it, seen starts empty, num =5, seen.contains(5) checks empty list so this is O(1) since theres nothing to scan
    //          It returns flase so 5 gets added to seen then second iteration checks if 5 is in seen and now checks a list with exactly one element which is still O(1)
    //              not growing with n at all in this specific scenario, and it immediately returns true, exiting
    //          So the genuinely fast case isnt the dupe is early in a large list, its the list is tiny, specifically size 2
    //              For a fixed tiny input like that, the total work is a small constant, independent of how large some other, different list of size n might have been
    //                  This is the actual subtlety: Omega describes the algs absolute best-case behavior across all possible inputs of a given methdos structure not the best position within one specific large n
    //          Since a 2 element input triggers the exit almost immediately, Omega here is Omega(1) not of n 
    //          Since Omega and O dont match, youd instead list best and worst case Theta separately, 1 best and n^2
    //      A HashSet<Integer> would be better since its more precise, just keys and not values
    //          Switching to HashSet makes .contains() O(1) avg, dropping the whole method to O(n) time
    // B
    public static boolean hasDuplicateSlow(List<Integer> list) {
        List<Integer> seen = new ArrayList<>();
        for (Integer num : list) {
            if (seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }
        return false;
    }
    // Func 3
    //    It creates 3 vars, all are ints of either 0 or some value of size n - 1, it has a while loop so since we didnt cover that I think that would be an O(1) check for that condition
    //          it increments steps, divides low + high by 2 for mid and conducts an O(1) check for the if condition, another checking if mid is less than target
    //          Reassigns low if its less and returns steps which just an int 
    //          Big O would be O(logn) for binary search, i need more explanation as of why each ase is a particular BigO
    //   For Omega, there is an early return if the array inputted was empty which would be its fastest case so o(1) for that
    //   Since there is no theta since O and Omega are not the same, worst case for this would be the equivalent to big o
    //   For aux space, itll be O(1) since there is one int var being returned
    //   Why bineary search is O(logn)
    //      Every single iteration of the while loop cuts the remaining search space in half
    //      Starting with n elements, after one iteration you have n/2 left, after two iterations n/4 etc
    //      The question "how many times can you halve n before reaching 1 element" is precisely what the logarithm answers- log2(n) is defined as
    //          the number of times you must divide n by 2 to reach 1
    //      So the loop runs at most log2(n) times, and since BigO drops the base of the logarithm (changing bases only multiplies by a constant, which BigO also ignores), O(logn) stands
    //      Any logarithm that repeatedly discards a constant fraction of its remaining problem size is O(logn)
    //   The empty-array reasoning corrected
    //      If the array was size 0 that would mean the condition checks 0 <= -1 which is false thus the loop never runs and just returns 0
    //          This isn't an early return this is just not starting the loop which is an even more extreme case than what was described above
    //      Thus this condition check is still Omega(1) best-case even on an empty array
    public static int binarySearchCount(int[] sortedArr, int target) {
        int low = 0, high = sortedArr.length - 1, steps = 0;
        while (low <= high) {
            steps++;
            int mid = (low + high) / 2;
            if (sortedArr[mid] == target) return steps;
            else if (sortedArr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return steps;
    }
    // Func 4
    // This func creates no vars, aux space is O(1) for an int value
    // BigO is O(n) due to the loop with an O(1) condition check
    // Omega would be o(1) due to that early check which would be if the first index in the array is equal to the target
    // No theta since they differ at best case Omega(1) but there would be for worst case Omega(n) if no indexes in the array is equal to target
    // Things worth fixing
    //      Omega is sepcifically the best-case bound, so worst case Omega isnt something
    //      What is meant is that the worst case here is Theta(n) since restricting attention to only the worst case scenary, its best and worst are trivally the sam
    //          its always exactly n comparisons when the target is absent, while the overall method has no single theta because best/worst differ
    public static int findFirstOrLast(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Exercise 2 - Writing to a Target Complexity, Not Just Reading It
    // Three separate methods, each with a stated target complexity
    //      Method 1 - O(n) time, O(1) auxiliary space
    //          Given an int[], return the max value in the array
    //          The O(1) space constraint is the actual constraint worth taking seriously, no new array/list, nothing that scales with n
    public static int findMaxFrom(int[] array) {
        int currentMax = array[0];
        for (int indice: array) {
            if (indice > currentMax) { currentMax = indice; }
        }
        return currentMax;
        // confirmed that the loop is O(n) time with an O(1) check in the if condition, aux space is O(1) with an int var being returned
    }
    //      Method 2 - O(n) time, O(n) aux space
    //          Given an int[], return a new array where each ele is the original value doubled
    //          This one genuinely needs O(n) space, since output must hold n values
    //          The execise here is recognizing that O(n) space isnt always avoidable, and forcing O(1) onto a problem that structurally requires new output would be wrong
    public static int[] returnOrig_Doubled(int[] with) {
        int[] returnArr = new int[with.length];
        for (int i = 0; i < with.length; i++) {
            returnArr[i] = with[i] * 2;
        }
        return returnArr;
        // confirmed that loop is O(n) with two O(1) retrievals; a particular index from both arrays in order to get/set respectively
        // Aux space is O(n) since it depends on the size of the input array
    }
    //      Method 3 - O(n) time
    //          Given an int[], determine whether the array is a palindrome
    //          Use 2 pointers, one starting at index 0, the other at last index, moving toward each other, comparing vlaues each step
    //          Ask yourself, what are the starting pos of each pointer, whats compared at each step, whats loops exact stoping condition
    public static boolean checkIfPalindrome(int[] from) {
        int right = from.length - 1;
        for (int left = 0; left < from.length; left ++) {
            // if they are different then return
            if (left == right) { return true; } else if (from[left] != from[right]) { return false; } else { right--; }
        }
        return true;
        // this was genuinely cool, i had implemented right to be within the scope of the for loop but that is reset after each iteration to the length of the input - 1 
        // so it remained the same, knew base case was if they met such as index of size 1, next case was if they differed, then decremented right
        // this is 0(n) with the for loop, multiple O(1) checks and aux space is o(1) with the boolean value, omega would be the same as big o since the size of the input is depended on
        // thus theta is the same
        // the else if here would actually make Omega(1) best case since its an eearly return and Omega(n) worst case; going through whole array
    }

    // Exercise 3 -- Complexity Comparison, Same Problem, Two Different Approaches
    // Given an int[], find two elements that sum to a specific target value
    // Version 1 - the brute force nested loop approach: check every pair
    //      State its actual time and space complexity, with full trace (llop counts, early exit or not)
    //              This is a nested so BigO is n^2, there is an early return so omega would be 1 considering if first two are the necessary vals, theta theres no theta
    //              Aux space is 1 since were returning a known size array of either 0 or 2
    // Version 2 - single pass hashmap approach.
    //      state its time and space complexity the same way
    //      Aux space is the same as above, Big O is n due to the for loop and O(1) conditional checks within, omega is the same so theta(n)
    // Then, determine which one is better and is that a fixed answer or does it depend on something about the input size, memory constraints, or whether the array is already sorted
    //      since the nested for loop is more like a sliding window, it doesnt return the first possiblity, unless we started the j index at the end of the array
    //      but i like the hashmap approach better since it stores, the value at a particular index in arr and the index of it but first checks if it has encountered the difference
    // Tested with int[] variableUse = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1} and target of 12
    // Couple of notes worth keeping
    //      this isnt a sliding window, its just an exhaustive pairwise check
    //      HM is faster since O(n) is better than n^2 but costs O(n) space versus the brute forces O(1)
    public static int[] twoSum_V1(int[] arr, int target) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) { return new int[] {i, j}; }
            }
        }
        return new int[] {};
    }
    public static int[] twoSum_V2(int[] arr, int target) {
        HashMap<Integer, Integer> possibilities = new HashMap<>();
        for(int num = 0; num < arr.length; num++) {
            if (possibilities.containsKey(target - arr[num])) {
                return new int[] {possibilities.get(target - arr[num]), num};
            }
            possibilities.putIfAbsent(arr[num], num);
        }
        return new int[] {};
    }


    // Phase 2 - Arrays and Strings, Rebuilt From the Ground Up
    // Why contiguous memory is the root of everything about arrays
    //   An array reserves one unbroken block of memory, sized at creation
    //   Accessing index 1 is a single arithmetic step - baseAddress + (i * elementSize) which is why its O(1)
    //      theres no searching just a direct calculation landing exactly on the right memory address. This is the fact every other array behavior traces back to
    // Why insertion/deletion in the middle O(n)
    //      Since the block is contiguous with no gaps, inserting at position i means every element from i onward must physically shift one slot over to make room
    //          an O(n) cost in worst case (inserting near the front) even though the insertion itself is conceptually one new value
    // Two Pointers - the general pattern, not just the palindrome trick
    //      Two indices moving through a structure, either from oppsoite ends converging (palindrome check, sorted pair sum) or at different speeds from the same end
    //      The unifying idea is that two positions tracked simultaneously, lettting you avoid a nested loop that would otherwise be needed to compare/relate two different pos in data
    // Sliding Window - Genuinely new, mot yet covered adn directly the pattern underneat the club Lamp's problem
    //      A window is contiguous range [left, right]that expands/slides across the array, maintaining some running property without recalculating
    //          that property from scratch every time the window moves
    //      The efficiency win: instead of recomputing sum of the window freshly at every positon which ould be O(n) per pos, O(n^2) total
    //          you update the running value incrementally as window slides, subtract what leaves, add what enters, dropping the whole scan to O(n) total
    // The key thing to internalize from whats traced so far:
    //      i - k always points to the index thats leaving and i itself is the index entering
    //          the windows actual left edge at any point is - k + 1 but you never need to compute this directly
    //              since youre only ever tracking the sum, not the windows bounds themselved

    //  THE NEXT 4 EXERCISES ARE WITH FIXED-LEGNTH WINDOWS
    // Exercise 1 - Fixed-Size Sliding Window, Built From Scratch
    // Given an int[] of temperatures and a window size k
    // Return avg of highest-sum window of size k
    // Before starting:
    //     trace/write plan;
    //      what does the first loop need to compute to seed the window
    //          first needs to compute first k indices, store in var.
    //          O(n) with for loop, O(1) with getters, o(1) for int sum and int avg
    //      what does the second loops update line need to look like for this specific prob
    //          since were trying to get average we can keep the sum var, compute the avg,
    //          update avg with Math.max(maxAvg, windowAvg)
    //      Whats the time/space complexity targeted
    //          since we have two for loops that arent nested and a couple vars plus some arithmetic
    //              and returning only one var which would be the maxAvg the time complexity statys O(n)
    //              Space complexity is o(1)
                // time omega for this would be if the array given is empty or if the size of the array is equal to k so that would be o(n)
                // this method would have theta(n) regardless since it still uses o(n) for best case on omega
    public static double windowAverage(int[] from, int k) {
        // just taking a look at the structure of this, its kind of similar to Swifts for awaitWithtaskGroup
        double windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += from[i];
        }
        double maxAverage = windowSum / k;
        for (int i = k; i < from.length; i++) {
            windowSum += from[i] - from[i - k]; // starts at k - index 0
            maxAverage = Math.max(maxAverage, windowSum / k);
            // this above line is kind of like the .merge on a HM
            // like the first param would be resemble the old value, then the new value would be windowSum / k
        }
        return maxAverage;
    }
    // Exercise 2 - Fixed Window, Counting Instead of Summing
    // Given an int[] and a window size k, return the maximum count of even numbers found in any window of size k
    // Same subtract-leaves/add-enters mechanism but tracking a count instead of a sum
    //      the update needs to check whether the levaing element was even (decrement if so)
    //      and whether the entering element is even (increment if so)
    //      rather blindly adding/subtracting raw values
    // Before writing:
    //      trace/write plan:
    //          what does the first loop need to compute to seed the window
    //              the first loop grabs the first 3, reg for loop increment if even
    //          what does the second loops update line need to look like
    //              the leaving element here is arr[i - k], decrement if even
    //              the entering element is itself i
    //          whats the time/space complexity
    //              same as previous, O(n) time, returning a regular int, if conditions are O(1)
    //              no early returns thatll be implemented so Omega is n thus theta is also n
    public static int windowEvens(int[] arr, int k) {
        int maxNum_Evens = 0;
        for (int i = 0; i < k; i++) {
            if (arr[i] % 2 == 0) { maxNum_Evens += 1; }
        }
        int maxEvens = maxNum_Evens;
        for (int i = k; i < arr.length; i++) {
            if (arr[i - k] % 2 == 0) { maxNum_Evens -= 1; } // decrements if leaving int was even
            if (arr[i] % 2 == 0) { maxNum_Evens += 1; } // opposite for new int
            maxEvens = Math.max(maxEvens, maxNum_Evens);
        }
        return maxEvens;
    }
    // Exercise 3 - Fixed Window, Two Running Values at Once
    // Given an int[] and window size k, return the window (as a [start, end] index pair)
    //      with the largest range (max value - min value) within that specific window
    //          But do this without recalculating min/max from scratch each slide
    // Think through whether the simple subtract/add trick from sum-tracking actually works for min/max or whether it breaks down and why
    // Same process as prior two; explain before
    //     since i need min/max of each window to be passed down to 2nd layer with the other windows
    //          we can set a var to be this array as it currently is
    public static int[] greatestWindow(int[] arr, int k) {
        int currentGreatest = arr[0]; int currentLesser = arr[0];
        for (int i = 0; i < k; i++) {
            if (arr[i] > currentGreatest) { currentGreatest = arr[i]; }
            if (arr[i] < currentLesser) { currentLesser = arr[i]; }
        }
        for (int i = k; i < arr.length; i++) {
            for (int j = i; j < i + k && j + k < arr.length; j++) {
                if (arr[j] < currentLesser) { currentLesser = arr[i];} else if (arr[j] > currentGreatest) { currentGreatest = arr[j]; }
            }
        }
        return new int[] { currentLesser, currentGreatest };
    }
    // Exercise 4 - Fixed Window, Distinct Character Count
    // Given a String and window size k, return the count of distinct characters in the window with the most distinct characters across the whole striing
    // This is genuinely different form the sum/count excs - youre not tracking one running number, youre trackinga. collection of whats current present
    // Since distinct requires knowing which characters are in the winow, not just how many total characters passed through
    // Use HM<Character, Integer> mapping each char currently in the window to how many times it appears.
    //      On a character leaving, decrement its count and ciritically, if that count hits zero, remove the entry from the map entirely
    //      The size of the map itself at any moment is the distinct charcater count for that window - no separate counting needed
    public static int greatestOccurences(String s, int k) {
        HashMap<Character, Integer> distincts = new HashMap<>();
        HashMap<Character, Integer> bestWindow = new HashMap<>();

        char[] strArr = s.toCharArray();
        for(int i = 0; i < k; i++) { distincts.merge(strArr[i],1, (o, n) -> o + n); bestWindow = new HashMap<>(distincts);}
        int maxDistincts = distincts.size();

        for (int i = k; i < strArr.length; i++) {
            distincts.merge(strArr[i-k], 1, (o, n) -> o - n);
            if (distincts.get(strArr[i - k]) == 0) { distincts.remove(strArr[i-k]); }
            distincts.merge(strArr[i], 1, (o, n) -> o +n );

            if (distincts.size() > maxDistincts) { bestWindow = new HashMap<>(distincts);}
            maxDistincts = Math.max(maxDistincts, distincts.size());
        }
        System.out.println(bestWindow);
        return maxDistincts;
    }
    // Exercise 5 - Fixed Window in Two Dimensions
    // Given a 2D int[][] grid and a window size k, find the maximum sum of any k^2 contiguous square sub-grid
    // Think thorugh this before writing any code, since the naive extension of the 1D idea doesnt work cleanly here
    //      A 1D window slides in one direction, updating by dropping one element and adding one
    //      A 2D k^2 window sliding one step to the right doesnt just drop and add once cell;
    //          it drops an entire column of k cells on the left edge and adds an entire column of k cells on the right edge
    //      Thats still better than recomputing the whole sqaure from scratch but its a different layered version of the same idea, not a direct copy/paste of 1D update line
    // The actual approach worth building toward:
    //      first, for every row, compute a 1D sliding-window sum of width k across that row, giving you, for each row, the sum of every k-wide horiz strip starting at each column
    //      One you have that(essentially, a new smaller 2D array of row strip), apply a second 1D sliding window vertically
    //          summing k consecutive vals from that intermediate array to get actual k^2 sqaure sums
    // Before writing code, trace this:
    //      given a grid, what would the intermediate array (row-wise k-strip sums) actually look like in terms of demensions compared to original grid demsnions
    //      Once you have intermediate array, why does sliding vertically trhough it correctly give you k^2 sqaure sums rather than something else
    public static int[][] max2DSum(int[][] arrs, int k) {
        int[][] intermediateArr = new int[arrs.length][arrs.length - k +1];
        for (int row = 0; row < arrs.length; row++) {
            int windowSum = 0;
            for (int col = 0; col < k; col++) { windowSum += arrs[row][col]; }
            intermediateArr[row][0] = windowSum;

            for (int col = k; col < arrs[row].length; col++) {
                windowSum += arrs[row][col] - arrs[row][col - k];
                intermediateArr[row][col - k + 1] = windowSum;
            }
        }
       
        int[][] finalArr = new int[intermediateArr.length - k +1][intermediateArr[0].length];
        for (int col = 0; col < intermediateArr[0].length; col++) {
            // since the first row in the intermediateArr is the top of the grid it gives all columns
            int windowSum = 0;
            for (int row = 0; row < k; row++) {
                windowSum += intermediateArr[row][col];
                // this iterates through that first row and adds the col-index to windowSum
            }
            finalArr[0][col] = windowSum;

            for (int row = k; row < intermediateArr.length; row++) {
                windowSum += intermediateArr[row][col] - intermediateArr[row - k][col];
                // this gets the particular index of row row and subtracts the leaving row's col-index
                finalArr[row - k + 1][col] = windowSum;
            }
        }
        return finalArr;
    }


    // Phase 2 - Variable-Size Sliding Window - Full Explanation Before Any Exercise
    // A growing/shrinking window has no fixed size at all, instead, you maintain two pointers,
    //      left and right and the windows size is whatever right-left+1 happens to be at any time, changing dynamically based on some condition youre tracking
    // The general shape, nearly every variable window problem follows it
    /*
    int left = 0;
    for (int right = 0; right < arr.length; right++){
        1. add arr[right] into whatever youre tracking (sum, count, set)
        2. While the current window violates some condition
        while (violated condition) {
            Remove arr[left] from tracking
            left++
        }
        3. Now window is valid, do something with it (update a max/min, record of it)
    }
    */
   // The key strcuctural difference from fixed windows:
   //       right always advances, one step per outer loop iteration but left advances conditionally
   //           sometimes not moving at all in given iteration, sometimes moving multiple times in a row via the inner while
   //       The window expands when right moves without left needing to catch up and shrinks when the while loop fires and pulls left forward
   //           sometimes several times in a row until the condition is satisfied again
   // Concretely, the calssic version: find the length of the smallest contiguous subarray with a sum >= some target
   //      Why this needs a variable window
   //           you dont know in advance how wide the qualifying window needs to be 
   //               it depends entirely on the actual values in the array
   //           The window grows until the sum finally reaches the target and once it does, it shrinks (via while loop advancing left) to find the smallest window that still satisfies condition
   // Before any exercise;
   //       Trace through the template's logic in your own words;
   //           Why does right need to advance unconditionally every iteration while left advances conditionally inside a while loop 
   //               Because right traverses the array etc in order to check/explore the elements to see what satisfies the condition to be met
   //               For example in a target sum array to find the smallest arr say t = 5 and have 1,2,2,3,4
   //                   We know 1,2,2 is 5 but 2, 3 is also 5 and smaller so the window starts at 1,2 says its 3 then adds the other 2, hits 5 but size is 3
   //                   It needs to find the shortest arr so it adds the 3 and sum is 8, while condition is fired since it exceeds the target and moves left inward
   //                   Sum is 7 so shrinks left again and now the sum is 5 but in order for this example to workout we would need vars for sum, left, minLength, bestLeft, bestRight
   //                       then int[] res = Arrays.copyOfrange(arr, bestLeft, bestRight + 1) since range is exclusive on ending val
   public static int[] findShortest(int[] arr, int target) {
        int left = 0; int sum = 0;
        int minLength = Integer.MAX_VALUE;
        int bestLeft = -1; int bestRight = -1;
        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];

            // Shrink only when greater
            while (sum > target) { sum -= arr[left]; left++; }

            // Check for exact match after shrinking
            if (sum == target) {
                int currentLength = right - left +1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    bestLeft = left; bestRight = right;
                }
            }
        }
        if (bestLeft != 1) {
            return Arrays.copyOfRange(arr, bestLeft, bestRight +1);
        } else { return new int[] {}; }
   }
   // Refresher Exercise - Snack Streak (from a photo of a prblem from Gen-CIC)
   // Given a string s, find the length of the longest substring without repeating characters
   // Return that legnth
   // Use the general template: right grows the window by adding each new character
   // Track characters currently in the window a (HashSet<Character> works cleanly, same tool from distinct-characters window exc)
   // The while condition that shrinks left fires specifically when the character at right is already present in the window
   //       shrink left (removing characters from the set as they leave) until the duplicate is gone
   // Then add the new character and record the windows current size if its the best seen so far
   // need longestArray encountered var. using Max(currentVal, newVal)
   public static int findLongestSubstring(String s) {
    int bestLength = 0;
    HashSet<Character> encountered = new HashSet<>();

    int left = 0;
    for (int c = 0; c < s.length(); c++) {
        while (encountered.contains(s.charAt(c))) { encountered.remove(s.charAt(left)); left++; }
        encountered.add(s.charAt(c)); bestLength = Math.max(bestLength, c - left + 1);
    }

    return bestLength;
   }
   // Exercise 1 - Varaible window, Numeric Condition Instead of a Character Set
   // Given an int[] of positive numbers and a target value k
   //       find the length of the longest contiguous subarray whose product is strictly less than k
   // This inverts several things at once relative to the sum based version:
   //       youre maximizing instead of minimiing, tracking a running product instead of a sum (meaning the "remove what leaves" step is division, not subtraction)
   //       a different operation with its own consideration, since dividing by zero or by values that dont evenly divide could matter depending on how its thought about
   //           though since all values are positive integers here, division stays well
   //       and the shrink condition fires when the product becomes too large, not when some target has already been met
   // Before writing code, walk through by hand, with a small example (10, 5, 2, 6) k being 100, what left, right and running product do at each step
   //       specifically noting the moment the product would exceed 100 and twhat the shrink step needs to do about it
   public static String longestUnderSum(int[] arr, int k) {
    int currentProduct = 1; int left = 0; int maxLength = 0;

    for (int right = 0; right < arr.length; right++) {
        currentProduct *= arr[right];
        while (currentProduct >= k) { currentProduct /= arr[left]; left++; }
        maxLength = Math.max(maxLength, right - left + 1);
    }
    return String.format("There are %d numbers that multiply to %d", maxLength, currentProduct);
   }
   // Exercise 2
   // Given an int[] and target sum l, find the length of the longest contiguous subarray whose sume quals exactly k - not at least, less than but exactly equal to
   //       THis is harder than both previous exercises because equality-based conditions dont map cleanly onto the simple "shirnk while too big" or "shrink while dupes present"
   //       Sum can overshoot k and theres no guarantee shrinking will ever land exactly back on it using the basic window alone, especially if array contains negative numbers,
   //           which breaks the assumption that growing the window always increases the sum monotonically
   // Given this real complication, before writing any code, answer this:
   //       Does the standard two pointer variable window template actually work correctly for this problem if the array can contain negative numbers?
   //       Think through why/why not since answer determines whether this problem can be solved with sliding window at all, or whether it needs a fundamentally different approach
    public static void findLongestCont(int[] nums, int target) {
        HashMap<Integer, Integer> prefixIndex = new HashMap<>();
        prefixIndex.put(0, -1);
        int prefixSum = 0; int maxLength = 0;
        for (int right = 0; right < nums.length; right++) {
            prefixSum += nums[right];
            if (prefixIndex.containsKey(prefixSum - target)) {
                int length = right - prefixIndex.get(prefixSum - target);
                maxLength = Math.max(maxLength, length);
            }
            prefixIndex.putIfAbsent(prefixSum, right);
        }
        System.out.println(prefixIndex);
    }
   // Exercise 3
   // Given an int[] of positive integers and a target sum k, find the length of the longest contiguous subarray whose sum is less than or equal to k
   // Before writing code, state the two tracking variables needed and describe what the while condtion checks and what happens inside it
    public static int[] longestContSum(int[] nums, int target) {
        int left = 0; int sum = 0;
        int bestLeft = -1; int bestRight = -1;
        int maxLength = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > target) { sum -= nums[left]; left++;}
            if (sum <= target) {
                if ((right - left + 1) > maxLength) {
                    maxLength = (right - left + 1);
                    bestLeft = left; bestRight = right;
                    System.out.println(String.format("BL: %d, BR: %d", bestLeft, bestRight));
                }
            }
        }
        return Arrays.copyOfRange(nums, bestLeft, bestRight + 1);
    }
    // Exercise 3 - difficulty slightly incresing from Exc 2. Total Remains 12
    // Given a String and an integer k, find longest substring that contains at most k distinct characters
    //      The shrink condition checks the size of the map itself (how many distinct characters are present) not a duplicate check or a numeric sum/product threshold
    // Before writing code, state the plan: 
    //      What does the while loop condition check this time (in terms fo the maps size versus k)
    //      What needs to happen isnde the while loops body when a character's count hits zero after decrementing
    //          same removal logic as the early distinct-character execise, just not driving the shrink trigger itself rather than being computed and reported at the end
    public static int longestConformingSubString(String s, int k) {
        int left = 0; HashMap<Character, Integer> conformingSubstring = new HashMap<>();
        int maxLength = 0;
        for (int c = 0; c < s.length(); c++) {
            conformingSubstring.merge(s.charAt(c), 1, (o, n) -> o +n);

            while (conformingSubstring.size() > k) {
                conformingSubstring.merge(s.charAt(left), -1, (o, n) -> o + n);
                if (conformingSubstring.get(s.charAt(left)) == 0) {
                    conformingSubstring.remove(s.charAt(left));
                }
                left++;
            }
            maxLength = Math.max(maxLength, c - left + 1);
        }
        return maxLength;
    }
    // Exercise 4 - increasing difficulty
    // Given an int[], find the length of the longest subarray containing at most 2 distinct values
    //      same map size driven shrink mechanishm as prior but appied to numbers isntead of chars
    //      and generalized from at most k down to a fixed constraint of exactly 2
    // The actual increase in difficulty: this is the classic setup for a follow up contraint worth thinking through nor rather than after
    //      what would need to change in your approach if the problem instead asked for at most 2 distinct values and each of those two values must appear an equal nuber of times
    //          dont solve this just yet, just reason about whether your current map absed approach could be extended to check it or whether it would need something additional
    // Build base version first, same three step structure, HM and shrink condition when map.size() > 2
    public static HashMap<Integer, Integer> findIntSubArr(int[] nums, int k) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        int left = 0; int maxLength = 0;
        for (int right = 0; right < nums.length; right++) {
            freq.merge(nums[right], 1, (o, n) -> o + n);

            while (freq.size() > k) {
                freq.merge(nums[left], -1, (o, n) -> o + n);
                if (freq.get(nums[left]) == 0) { freq.remove(nums[left]); }
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        System.out.println(String.format("Max length is  %d", maxLength));
        return freq;
    }
    // Exercise 5 - difficulty increasing
    // Given a String consisting only of the characters 0 and 1, find the length of the longest substring containing at most one 0 that can be turned into all 1s
    //      meaning the longest stretch where youre allowed to flip at most one zero to a one and every other character must already be a one
    // This is a genuinely different shape of tracking than anything so far
    //      instead of a map counting distinct values, youre tracking a single running count of how many zeros are currently inside the window and the shrink condition fires the moment that count
    //          exceeds your allowance (1, in this version)
    // Before writing anything - state the plan yourself:
    //      what single variable do you need to track (not a map this time, think back to Exc 1 simpler numeric tracking)
    //          it can be any length of 1's with one 0 at any point and if theres another 0 adjacent then decrement to that 0
    //      what does the while condition check against
    //          whenever 
    //      what happens to that counter specifically when the character leaving the window at left happens to be a 0
    public static int longestBinarySubArr(String s) {
        int left = 0; int maxLength = 0; int occurences = 0;

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) == '0') { occurences +=1;}

            while (occurences > 1) { if (s.charAt(left) == '0') { occurences--; } left++; }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        // Tested with 0001010101011100011101
        return maxLength;
    }
    // Exercise 6 - Increased Difficutly
    // This one introduces a new mechanishm, explanation below
    // Longest Repeating Character Repalcement
    //      Given a String and an integer k, find the length of the longest substring that could be turned into all the same character by changing at most k characters
    // The key insight this problem needs, which nothing built so far has required
    //      inside the window, you dont just track how many distinct characters exit but you also need to track the count of whichever single character currently appears most often in the window. Call it maxFreq
    // Why this number matters
    //      if the windows size is right - left + 1 and the most ocmmon character inside it occurs maxFreq times, then the number of characters youd need to change to make
    //          the whole window that one repeated character is exactly windowSize - mexFreq, everything that isnt the majority character
    //      The window stays valid as long as windowSize - maxFreq <= k: the shirnk condition fires when windowSize - maxFreq > k
    // maxFreq does not need to be perfectly recalculates every time the window shirnks, its mathematically safe to let it be a little stale, never decreasing it even if the character it refers to has since left the window
    //      because a stale, too high maxFreq can only ever make the alg undercount how much shrinking is needed , which non-obviously still produces the correct final maxLength
    //          since the windows tru best length was already captured before the staleness could matter
    // This is a subtle correctness arg specific to this problem, not something to derive fromm scratch
    // Restate before building; what two things does the map need to track and how do you get maxFreq from it at any given meomen and whats the actual shrink condition
    public static String changeKLetters(String s, int k) {
        int maxFreq = 0; int left = 0; int maxLength = 0;
        HashMap<Character, Integer> occurences = new HashMap<>();
        for (int right = 0; right < s.length(); right++) {
            occurences.merge(s.charAt(right), 1, (o, n) -> o + n);
            maxFreq = Math.max(maxFreq, occurences.get(s.charAt(right)));

            while (((right - left + 1) - maxFreq) > k) {
                occurences.merge(s.charAt(left), -1, (o, n) -> o + n);
                if (occurences.get(s.charAt(left)) == 0) { occurences.remove(s.charAt(left)); }
                left++;
            }
            System.out.println(occurences);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        // tested with "alibaba"
        System.out.println(String.format("Map: %s, Max Freq: %d", occurences, maxFreq));
        return String.format("Max Length: %d", maxLength);
    }
    // Exercise 7, increasing difficulty
    // Given an int[] of positive integers, find the number of contiguous subarray whos product is strictly less than a given k, not the longest subarray
    // This is genuinely different question shape than everything so far, worth thinking through carefuly before writing
    // This one asks you to count every valid subarray, and theres a real trick worth derviing yourself:
    //      once you have a valid window [left, right] where the product is < k, how many additional valid subarrays does that single subarray actually represent
    //          just by considering different starting points within that same window, all ending at right
    // Before writing any code, work thorugh this with a small example
    //      if your current valid window spans indices 2 through 5, (four elemnts: left = 2 and right = 5)
    //          how many distinct contiguous subarrays end exactly at index 5 and start somewhere between index 2 and index 5 inclusive
    //      List them out explicity by their start/end index pairs
    /*@
     @ requires k > 1;
     @ requires (\forall int i; 0 <= i && i < nums.length; nums[i] != 0);
    @*/
    public static int findValidSubs(int[] nums, int k) {
        int count = 0; int left = 0; int prod = 1;
        for (int right = 0; right < nums.length; right++) {
            prod *= nums[right];
            
            while (prod >= k) {
                prod /= nums[left];
                left++;
            }
            count += (right - left + 1);
        }
        return count;
    }
    // Exercise 8 - difficulty increasing
    // Given an int[] and a target k, find total number of contiguous subarrays whose sum equals exactly k
    //      This time youre allowed to assume the array contains only positive integers which changes everything about which technique actually applies here
    // Think thorugh carefully before building since its a genuine trap
    //      does the standard two pointer growing shrinking window actually work correctly for an exact equality condition even with all positive numbers
    // or does exactly equals k behave differently from at most k or less than k in a way that breaks the shrink while invalid logic
    public static int findValidSubsAdd(int[] nums, int k) {
        HashMap<Integer, Integer> differences = new HashMap<>();
        int left = 0; int count = 0; int sum = 0;
        differences.put(0,1);
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            if (differences.containsKey(sum - k)) {
                count += differences.get(sum - k);
            }
            differences.merge(sum, 1, (o, n) -> o + n);

        }
        return count;
    }
    // Exercise 9 - same difficulty as Exc 8, increased excs to 14
    // Given an array of positive integers and a target k, find the total number of contiguous subarrays whose sum is a multiple of k meaning sum % k == 0
    //      same prefix sum plus map tehcnique but the thing youre checking for existence is based on the remainder when dividing the running sum by k
    // Before writing code, reason through why:
    //      if two difference positions have prefix sums that leave the same remainder when divided by k, what does that tell you about the sum of th eusbarray between those two positions
    //          specifically regarding whether its evenly divisible by k
    public static int findValidModulos(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int count = 0; int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            if (map.containsKey(sum % k)) {
                count += map.get(sum % k);
            }
            map.merge(sum % k, 1, (o, n) -> o + n);
        }
        System.out.println(map);
        return count;
        // when a remainder occurs n times total, the number of valid pairs it contributes is n choose 2 (n(n-1)/2) or n^2 - n / 2
    }
    // Exercise 10 - difficulty increasing, total stands at 14
    // Given a String consisting only of binary numbrs, find total number of contiguous substrings where the count of 0s equals the count of 1's
    // Think through this one carefully before building
    //      the underlying trick is genuinely the same prefix sum plus map idea but the sum itself isnt a direct running total of characters anymore
    // Work out what transformation turns cunt zeros equals count ones into something yo can track as a signle running numberm the same way sum divisible by k got reduced to tracking remainders
    // Consider: what if encountering a 1 countes as +1 and encountering a 0 counted as -1
    // What would it mean in terms of that running total for a substring to contain an equal number of each character
    public static int equalBinaryString(String binString) {
        int count = 0; int sum = 0;
        HashMap<Integer, Integer> occurences = new HashMap<>();
        occurences.put(0, 1);
        for (int right = 0; right < binString.length(); right++) {
            if (binString.charAt(right) == '1') { sum++; } else { sum--; }
            if (occurences.containsKey(sum)) { count += occurences.get(sum); }
            System.out.println(String.format("O: %s, S: %d", occurences, sum));
            occurences.merge(sum, 1, (o, n) -> o + n);
        }
        return count;
    }
    // Exercise 11, difficulty increasing
    // Given an int[], find the legnth of the longest strictly increasing contiguous subarray, meaning each element must be strictly greater than the one immediately before it, no repeats allowed within it
    // Theres no map involved at all here, no prefix sum, no distinct-value tracking
    // Think through why:
    //      the validity of this window dpeends only on the relationship between two adjacent elements, is the current one greater than the previous one, not any running total count or set membership at all
    // Before writing any code, reason through this:
    //      Given that the condition only ever depends on comparing arr[right] against arr[right - 1], does this problem actually need a left/right two pointer structure growing and shrinking at all
    //          or could it be solved with something simpler such as a single pass, tracking just the current streaks lenght and the best one seen?
    //      Think through whehter shrinking even makes sense as a concept here, given that the moment the icnreasing condition breaks, the entire current streak ebcomes invalid at once, not something youd want to shirnk from 1 side
    public static int findLongestIncreasing(int[] nums) {
        int maxLength = 1; int currentLength = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[ i -1]) { currentLength++; } else { currentLength = 1; }
            maxLength = Math.max(maxLength, currentLength);
        }
        return maxLength;
    }
    // Exercise 12 - difficulty increasing, total remains at 14
    // Given an int[], find length of longest contiguous subarray that is either strictly increasing or strictly decreasing, meaning check both directions and return whichever streak increasing/decreasing is longest across the whole array
    // THink through before building, does this genuinely require two separate passes over the array (on tracking increasing streaks, one tracking decreasing) or can both be tracked simultaneously
    //      in a single pass using two independent counters that both reset and grow bases on different conditions at each step
    // Consider what happens at each index, could the same element potentially extend an increasing streak and simultaneously break a decreasing one or vice versa
    //      meaning both counters need their own independent logic evaluated at every single iteration, not sharing one rest/increment decision
    public static int findLongestDeIncreasing(int[] arr) {
        int totalMaxLength = 1;
        int maxDecreasing = 1; int maxIncreasing = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                maxIncreasing = 1;
                maxDecreasing++;
            } else {
                maxDecreasing = 1;
                maxIncreasing++;
            }
            totalMaxLength = Math.max(totalMaxLength, Math.max(maxDecreasing, maxIncreasing));
        }
        System.out.println(String.format("-%d, +%d", maxDecreasing, maxIncreasing));
        return totalMaxLength;
    }
    /*
        // This exercise will be conducted whenever the correct ds are introduced
        Given an int[], find the length of the longest contiguous subarray where every element is strictly greater than the average of the entire subarray. 
            This forces a genuinely different kind of thinking than anything in this set so far — unlike every prior "streak" or "window" condition, 
                this one's validity depends on a relationship every element has with a value computed from the whole subarray itself, not a simple adjacent 
                    comparison or a running count/sum threshold against a fixed external target.
        Think through this carefully before building anything, since it's worth recognizing a real structural trap: can this condition be checked incrementally,
            the way every sliding-window exercise so far has (grow one element, cheaply update a running value, decide validity) — or does checking "is every
                element greater than this subarray's own average" fundamentally require knowing the full subarray's contents before you can evaluate it at all,
                    meaning any candidate window would need to be checked as a whole, not built up incrementally with cheap per-step updates?
        Reason through it with a small example: take [5, 6, 7] — compute the average, then check whether every element exceeds it. Now imagine adding a fourth
            element to extend this into [5,6,7,8] — does knowing the previous window's average let you cheaply determine anything about the new, larger window's
                validity, or does the entire condition need to be re-evaluated from scratch?
     */
    // Exercise 13 - difficulty increasing total remains 14
    // Given an int[] and target k, find the length of the shortest contiguous subarray with sum greater than or equal to k
    //      genuinely the mirror image of every longest variant built so far tonight, using Math.min instead of Math.max and shrinking while condition is already satisfied
    //          (trying to shrink further, since youre now hunting for the smalles valid window, not the largest) rather than shrinking to escape an invalid state
    public static int findShortestSub(int[] arr, int k) {
        int left = 0; int sum = 0; int shortestLength = Integer.MAX_VALUE;
        for (int right = 0; right < arr.length; right++) {
            sum += arr[right];
            while (sum >= k) { shortestLength = Math.min(shortestLength, right - left + 1); System.out.println(Arrays.toString(Arrays.copyOfRange(arr, left, right + 1))); sum -= arr[left]; left++; }
        }
        return shortestLength;
    }
    // Exercise 14, closing exc for this set.
    // Given an int[] and integer k, find max sum achievable form any contiguous subarray of length exactly k but with one added twist
    //      youre also given a second int[] of the same length as the original array, representing a penalty value at each index
    // If your chosen window includes index 1, you must subtract penalty[1] from that windows sum
    //      but only for the single highest penalty value found within that specific window (not every penalty in the window, just the worst one)
    //          representing "you get penalized once per window, for your single riskiest included position"
    // THis deliberatly combines two things proven separately:
    //      the fixed size sum tracking window and a running maximum within the window tracking mechanish similar in spirit to the min/max range problem
    //          except this time, since window size is fixed at k rather than variable, think carefully about whether the no simple arithmetic shortcut for sliding min/max limitation still applies
    //          or whether the fixed size constraint changes whats genuinel feasible without needing a monotonic deque
    // before writing any code, reason through this explicitly:
    //      given everything already proven about sliding max/min not having a cheap O(1) update trick
    //          is it acceptable for this specific exercise to fall back to a full rescan of the current windows k elements to find the max penalty each time the window slides
    //              making this an intentionalyy O(N*K) solution rather than a fully optimal one
    
}
 