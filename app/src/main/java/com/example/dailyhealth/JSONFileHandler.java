package com.example.dailyhealth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONFileHandler {
    private static final String FILE_NAME = "exercises.json";

    // Phương thức để đọc dữ liệu từ file JSON và trả về một danh sách các bài tập lớn
    public static ArrayList<MainExercise> readMainExercisesFromJSON() {
        ArrayList<MainExercise> mainExercises = new ArrayList<>();

        try (FileReader fileReader = new FileReader(FILE_NAME)) {
            JSONArray jsonArray = new JSONArray(fileReader);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String largeExerciseName = jsonObject.getString("largeExerciseName");
                int exerciseDuration = jsonObject.getInt("exerciseDuration");
                int exerciseDifficulty = jsonObject.getInt("exerciseDifficulty");

                JSONArray smallExercisesArray = jsonObject.getJSONArray("smallExercises");
                ArrayList<SmallExercise> smallExercises = new ArrayList<>();
                for (int j = 0; j < smallExercisesArray.length(); j++) {
                    JSONObject smallExerciseObject = smallExercisesArray.getJSONObject(j);
                    int smallExerciseCode = smallExerciseObject.getInt("smallExerciseCode");
                    String smallExerciseName = smallExerciseObject.getString("smallExerciseName");
                    int exerciseType = smallExerciseObject.getInt("exerciseType");
                    String imageFileName = smallExerciseObject.getString("imageFileName");
                    Integer exerciseDurationSmall = smallExerciseObject.optInt("exerciseDuration", 0);
                    Integer exerciseRepetitions = smallExerciseObject.optInt("exerciseRepetitions", 0);
                    SmallExercise smallExercise = new SmallExercise(smallExerciseCode, smallExerciseName, exerciseType,
                            imageFileName, exerciseDurationSmall, exerciseRepetitions);
                    smallExercises.add(smallExercise);
                }
                MainExercise mainExercise = new MainExercise(id, exerciseDuration, exerciseDifficulty, largeExerciseName, smallExercises);
                mainExercises.add(mainExercise);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return mainExercises;
    }

    // Phương thức để ghi dữ liệu vào file JSON từ danh sách các bài tập lớn
//    public static void writeMainExercisesToJSON(ArrayList<MainExercise> mainExercises) {
//        JSONArray jsonArray = new JSONArray();
//        for (MainExercise mainExercise : mainExercises) {
//            jsonArray.put(mainExercise.toJSONObject());
//        }
//
//        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
//            fileWriter.write(jsonArray.toString());
//            fileWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

