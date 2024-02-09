import java.util.Scanner;

public class StudentGradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student Grade Calculator");
        System.out.println("------------------------");

        
        double assignmentScore = inputScore(scanner, "Assignments");
        double quizScore = inputScore(scanner, "Quizzes");
        double examScore = inputScore(scanner, "Exams");

     
        double totalScore = calculateTotalScore(assignmentScore, quizScore, examScore);

       
        char grade = calculateGrade(totalScore);

       
        System.out.println("------------------------");
        System.out.println("Total Score: " + totalScore);
        System.out.println("Grade: " + grade);

        scanner.close();
    }

   
    private static double inputScore(Scanner scanner, String categoryName) {
        System.out.print("Enter score for " + categoryName + ": ");
        return scanner.nextDouble();
    }

    
    private static double calculateTotalScore(double assignmentScore, double quizScore, double examScore) {
        // You can adjust the weights of each component according to your grading scheme
        double totalScore = (assignmentScore * 0.4) + (quizScore * 0.3) + (examScore * 0.3);
        return totalScore;
    }

  
    private static char calculateGrade(double totalScore) {
        if (totalScore >= 90) {
            return 'A';
        } else if (totalScore >= 80) {
            return 'B';
        } else if (totalScore >= 70) {
            return 'C';
        } else if (totalScore >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}
