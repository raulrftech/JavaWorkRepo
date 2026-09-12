package Day5;

public class day5 {
    public static void main(String[] args) {

    }

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
    // Worth a real correction to something used loosely all along
    //      HashMap's.get() is O(!) is actually informally standing in for Theta(1) average case - 
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
    // Best/worst/average, why the distinction is practically load-bearing, not academic
    //      Quciksort (coming in Phase 9) has an O(n^2) worst case with unlucky pivot choices but an average case of O(nlogn) - which is precisely why it's used constantly in practice despute that bad worst case;
    //          average behavior, not worst-case paranoia, drives most real tool choice
}
