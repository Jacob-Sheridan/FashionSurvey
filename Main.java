// Driver Class
public class Main {
	// Main function
	public static void main(String[] args) {
        FashionSurvey survey = new FashionSurvey();
        survey.OutfitManager("../images/outfits.csv");
		survey.drawWindow(survey.outfits.get(0).imageLocation, 0);
	}
}
