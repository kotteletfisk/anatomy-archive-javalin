package dat.controller;

import dat.dao.EquipmentDao;
import dat.dao.ExerciseDao;
import dat.dao.ExerciseTypeDao;
import dat.dao.MuscleDao;
import dat.dto.EquipmentDTO;
import dat.dto.ExerciseDTO;
import dat.dto.ExerciseTypeDTO;
import dat.dto.MuscleDTO;
import dat.entities.Equipment;
import dat.entities.Exercise;
import dat.entities.ExerciseType;
import dat.entities.Muscle;
import dat.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class ExerciseController
{
    ExerciseDao exerciseDAO = ExerciseDao.getInstance();
    ExerciseTypeDao exerciseTypeDAO = ExerciseTypeDao.getInstance();
    MuscleDao muscleDAO = MuscleDao.getInstance();
    EquipmentDao equipmentDAO = EquipmentDao.getInstance();

    public void getAll(Context context) throws Exception
    {
        List<Exercise> exercises = exerciseDAO.readAll();
        if (exercises.isEmpty()) throw new ApiException(500, "Problem with database");
        List<ExerciseDTO> dtos = ExerciseDTO.toExerciseDTOList(exercises);
        context.status(200);
        context.json(dtos);
    }

    public void getById(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(id)) throw new ApiException(404, "Exercise with id " + id + " not found");
        Exercise exercise = exerciseDAO.read(id);
        ExerciseDTO dto = new ExerciseDTO(exercise);
        context.status(200);
        context.json(dto);
    }

    public void create(Context context) throws ApiException
    {
        ExerciseDTO dto = validate(context);
        Exercise exercise = exerciseDAO.create(new Exercise(dto));
        dto = new ExerciseDTO(exercise);
        context.status(201);
        context.json(dto);
    }

    public void update(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));

        if (!exerciseDAO.exists(id)) throw new ApiException(404, "Exercise with id " + id + " not found");
        ExerciseDTO dto = validate(context);
        Exercise exercise = new Exercise(dto);
        exercise.setId(id);
        dto = new ExerciseDTO(exerciseDAO.update(exercise));
        context.status(200);
        context.json(dto);
    }

    public void delete(Context context) throws ApiException
    {
        int id = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(id)) throw new ApiException(404, "Exercise with id " + id + " not found");
        exerciseDAO.delete(id);
        context.status(204);
    }

    public void addMuscle(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.queryParam("exerciseId"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        String muscleId = context.queryParam("muscleId");

        if (muscleId.contains(","))
        {
            String[] muscleIds = muscleId.split(",");
            int[] muscleIdsInt = new int[muscleIds.length];
            for (int i = 0; i < muscleIds.length; i++)
            {
                muscleIdsInt[i] = Integer.parseInt(muscleIds[i]);
                if (!muscleDAO.exists(muscleIdsInt[i])) throw new ApiException(404, "Muscle with id " + muscleIdsInt[i] + " not found");
            }
            exerciseDAO.addMuscleToExercise(exerciseId, muscleIdsInt);
        }
        else
        {
            int parsedInt = Integer.parseInt(muscleId);
            if (!muscleDAO.exists(parsedInt)) throw new ApiException(404, "Muscle with id " + muscleId + " not found");

            exerciseDAO.addMuscleToExercise(exerciseId, parsedInt);
        }


        context.status(201);
    }

    public void getMuscle(Context context) throws Exception
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        List<Muscle> muscles = muscleDAO.getMusclesByExercise(exerciseId);
        context.status(200);
        context.json(MuscleDTO.toMuscleDTOList(muscles));
    }

    public void addType(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.queryParam("exerciseId"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");

        String typeId = context.queryParam("typeId");
        if (typeId.contains(","))
        {
            String[] typeIds = typeId.trim().split(",");
            int[] typeIdsInt = new int[typeIds.length];
            for (int i = 0; i < typeIds.length; i++)
            {
                typeIdsInt[i] = Integer.parseInt(typeIds[i]);
                if (!exerciseTypeDAO.exists(typeIdsInt[i])) throw new ApiException(404, "Type with id " + typeIdsInt[i] + " not found");
            }
            exerciseDAO.addTypeToExercise(exerciseId, typeIdsInt);
        }
        else
        {
            int parsedInt = Integer.parseInt(typeId);
            if (!exerciseTypeDAO.exists(parsedInt)) throw new ApiException(404, "Type with id " + typeId + " not found");
            exerciseDAO.addTypeToExercise(exerciseId, parsedInt);
        }
        context.status(201);
    }

    public void getType(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        List<ExerciseType> exerciseTypes = exerciseDAO.getTypesByExercise(exerciseId);
        context.status(200);
        context.json(ExerciseTypeDTO.toExerciseTypeDTOList(exerciseTypes));
    }

    public void addEquipment(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.queryParam("exerciseId"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");

        String equipmentId = context.queryParam("equipmentId");
        if (equipmentId.contains(","))
        {
            String[] equipmentIds = equipmentId.split(",");
            int[] equipmentIdsInt = new int[equipmentIds.length];
            for (int i = 0; i < equipmentIds.length; i++)
            {
                equipmentIdsInt[i] = Integer.parseInt(equipmentIds[i]);
                if (!equipmentDAO.exists(equipmentIdsInt[i])) throw new ApiException(404, "Equipment with id " + equipmentIdsInt[i] + " not found");
            }
            exerciseDAO.addEquipmentToExercise(exerciseId, equipmentIdsInt);
        }
        else
        {
            int parsedInt = Integer.parseInt(equipmentId);
            if (!equipmentDAO.exists(parsedInt)) throw new ApiException(404, "Equipment with id " + equipmentId + " not found");
            exerciseDAO.addEquipmentToExercise(exerciseId, parsedInt);
        }
        context.status(201);
    }

    public void getEquipment(Context context) throws ApiException
    {
        int exerciseId = Integer.parseInt(context.pathParam("id"));
        if (!exerciseDAO.exists(exerciseId)) throw new ApiException(404, "Exercise with id " + exerciseId + " not found");
        List<Equipment> equipments = exerciseDAO.getEquipmentByExercise(exerciseId);
        context.status(200);
        context.json(EquipmentDTO.toEquipmentDTOList(equipments));
    }

    public void getByMusclePattern(Context context)
    {
        String musclePattern = context.queryParam("pattern");
        List<Exercise> exercises = exerciseDAO.getByMusclePattern(musclePattern);
        context.status(200);
        context.json(ExerciseDTO.toExerciseDTOList(exercises));
    }

    public void getByEquipmentPattern(Context context)
    {
        String equipmentPattern = context.queryParam("pattern");
        List<Exercise> exercises = exerciseDAO.getByEquipmentPattern(equipmentPattern);
        context.status(200);
        context.json(ExerciseDTO.toExerciseDTOList(exercises));
    }

    public void getByNamePattern(Context context)
    {
        String namePattern = context.queryParam("pattern");
        List<Exercise> exercises = exerciseDAO.getByNamePattern(namePattern);
        context.status(200);
        context.json(ExerciseDTO.toExerciseDTOList(exercises));
    }

    private ExerciseDTO validate(Context context) throws ApiException
    {
        return context.bodyValidator(ExerciseDTO.class)
                .check((exerciseDTO) -> exerciseDTO.getName() != null && !exerciseDTO.getName().isEmpty(), "Name cannot be empty")
                .check((exerciseDTO) -> exerciseDTO.getName().length() <= 50, "Name longer than 50 characters")
                .check((exerciseDTO) -> exerciseDAO.readByName(exerciseDTO.getName()) == null, "Name already exists")
                .get();
    }
}
