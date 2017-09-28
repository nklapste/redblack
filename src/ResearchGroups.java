/**
 * ECE325: Object Oriented Software Design <br />
 * Lab 2: Arrays and Red Black Tree (part 1: Arrays) <br />
 * The ResearchGroup class uses a 2D array to store the names of group members
 * Name: Nathan Klapstein
 * ID: 1449872
 */
public class ResearchGroups {
    // TODO: Write your searching and sorting methods here.

    public static void searchMember(String[][] groups, String name) {
        // init member found flag
        boolean found = false;

        // iterate through each subgroup
        for (int groupnum = 0; groupnum < groups.length; groupnum++) {
            String [] group = groups[groupnum];

            // iterate through each name within a subgroup
            for (int personnum = 0; personnum<group.length; personnum++) {
                String person = groups[groupnum][personnum];

                if(person.equals(name)) {
                    found = true;
                    System.out.printf("%s exists ", name);
                    if (personnum==0) {
                        System.out.print("as leader, ");
                    } else {
                        System.out.print("as follower, ");
                    }
                    System.out.printf("group number: %d group position: %d\n", groupnum, personnum);
                }
            }
        }

        // if requested member was not found make note
        if (!found) {
            System.out.printf("No such name: %s found!\n", name);
        }
        return;
    }


    /**
     * Swap two values within an array
     * @param groups   {@code String[][]} 
     * @param a   {@code int} index of the first element to be swapped
     * @param b	  {@code int} index of the second element to be swapped
     */
    public static void swap(String[][] groups, int a, int b) {
        String[] tmp = groups[a];
        groups[a] = groups[b];
        groups[b] = tmp;
    }


    /**
     * Building a heap to sort using implicit binary tree method
     * @param groups   {@code String[][]} The array of string arrays to be sorted
     * @param l   {@code int} The length limit (for loop iteration 
     * 					      limit) of the array to be sorted
     */
    public static void buildheap(String[][] groups, int l) {
        for (int i = 0; i <= l; i++) {
            int i_ = i;

            while (groups[i_].length > groups[(i_-1)/2].length  && i_ != 0) {
                swap(groups, i_, ((i_-1)/2));
                i_ = (i_-1)/2;
            }
        }
    }

    /**
     * The heap sort procedure on a array of string arrays
     * @param groups   {@code String[][]} The array of string arrays to be sorted
     */
    public static void sortGroups(String[][] groups) {
        // minus by one to deal with 0 indexing
        int length = groups.length-1;

        while (length > 0) {
            // step 1 build the heap
            buildheap(groups, length);

            // step 2 swap root with the last leaf
            swap(groups, 0, length);

            // iterate length down by 1 to lock
            // down solved components
            length = length-1;

            // step 3 heapify the rest
            buildheap(groups, length);
        }
    }


    public static void main(String[] args) {
        String[][] groups = { {"Bob", "Carol", "Eric", "Matt"},             // 0
                {"Jim", "Lucy", "Terry", "Brenda", "Ben"},    // 1
                {"Susan", "Brad", "Jim"},                     // 2
                {"Sue", "Wendy", "Sam"},                      // 3
                {"Kate", "Jack", "James", "Sydney"},          // 4
                {"Mohammad", "Tim", "Kian"},                  // 5
                {"Emma", "Carol"},                            // 6
                {"Nick", "Osama", "Harry", "Ben"},            // 7
                {"Mary", "John", "Ricky"} };                  // 8

        // test searchMember 
        searchMember(groups, "Bob");
        searchMember(groups, "Emma");
        searchMember(groups, "Ben");
        searchMember(groups, "INVALID_NAME");

        // test sortGroups
        sortGroups(groups);

        for (int i=0;i<groups.length;i++) {
            System.out.print("group " + i + ": ");
            for (int j = 0; j < groups[i].length; j++)
                System.out.print(groups[i][j] + " ");
            System.out.println();
        }
    }

}