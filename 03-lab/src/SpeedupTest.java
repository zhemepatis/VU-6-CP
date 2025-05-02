public class SpeedupTest {
    private static final String[] TASK_SIZE = new String[] {"1000", "10000", "100000"};
    private static final String[] THREAD_NUM = new String[] {"1", "2", "4", "8", "16"};

    public static void main(String[] args) throws Exception {
        int sizeCasesNum = TASK_SIZE.length;
        int threadCasesNum = THREAD_NUM.length;

        for (int i = 0; i < sizeCasesNum; ++i) {
            for (int j = 0; j < threadCasesNum; ++j) {
                System.out.println("\nTASK SIZE: " + TASK_SIZE[i] + ", THREADS: " + THREAD_NUM[j]);

                String[] argsList = new String[] {"16", TASK_SIZE[i], THREAD_NUM[j], "0"};
                GameOfLife.main(argsList);
            }

            System.out.println("\n----------");
        }
    }
}
