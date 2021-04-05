package net.thumbtack.school.threads;

import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingErrorCode;
import net.thumbtack.school.ttschool.TrainingException;
import java.util.*;
import java.util.stream.Collectors;

// экземпляры этого класса полностью потокобезопасны
// теперь вызовы методов одного и того же экземпляра этого класса будут потокобезопасны
class Group {
    private String name;
    private String room;
    private List<Trainee> traineeList;

    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        traineeList = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) throws TrainingException {
        if (name==null || name.isEmpty())
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        this.name = name;
    }

    public synchronized String getRoom() {
        return room;
    }

    public synchronized void setRoom(String room) throws TrainingException{
        if (room==null || room.isEmpty())
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        this.room = room;
    }

    public synchronized List<Trainee> getTrainees() {
        return traineeList;
    }

    public synchronized void addTrainee(Trainee trainee) {
        traineeList.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException{
        if (!traineeList.remove(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTrainee(int index) throws TrainingException {
        if (traineeList.size() <= index) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        traineeList.remove(index);
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException{
        synchronized (traineeList) {
            for (Trainee trainee:traineeList) {
                if (trainee.getFirstName().equals(firstName)) {
                    return trainee;
                }
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee  getTraineeByFullName(String fullName) throws TrainingException {
        synchronized (traineeList) {
            for (Trainee trainee:traineeList) {
                if (trainee.getFullName().equals(fullName)) {
                    return trainee;
                }
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);

    }

    public void sortTraineeListByFirstNameAscendant() {
        synchronized (traineeList) {
            traineeList.sort(Comparator.comparing(Trainee::getFirstName));
        }
    }

    public synchronized void sortTraineeListByRatingDescendant() {
        traineeList.sort((p1,p2)-> -1*Integer.compare(p1.getRating(),p2.getRating()));
    }

    public synchronized void reverseTraineeList() {
        Collections.reverse(traineeList);
    }

    public synchronized void rotateTraineeList(int positions) {
        Collections.rotate(traineeList,positions);
    }

    public synchronized List<Trainee>  getTraineesWithMaxRating() throws TrainingException{
        if (traineeList.isEmpty()) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        traineeList.sort(Comparator.comparing(Trainee::getRating).reversed());
        List<Trainee> maxList = null;
        maxList = streamList(maxList,5);
        return maxList;
    }

    //.stream() тоже переопределен с блоком синхронайзд
    public synchronized List<Trainee>  streamList (List<Trainee> maxList, int numb){
        Integer max = numb;
        maxList = traineeList.stream().filter(trainee -> trainee.getRating()==max).collect(Collectors.toList());
        if (maxList.isEmpty()) {
            maxList = streamList(maxList, --numb);
        }
        return maxList;
    }


    public boolean  hasDuplicates() {
        Set<Trainee> temp = new HashSet<>(traineeList);
        // А если здесть кот-то успел поменять длинну списка?
        // выходит нужен потокобезопасный прием найти дубли у Лист
        // или если методы меняющие список синхронизированы, то они будут видеть что "монитор" у этого Лист занять?
        return !(temp.size()==traineeList.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof net.thumbtack.school.ttschool.Group)) return false;
        net.thumbtack.school.ttschool.Group group = (net.thumbtack.school.ttschool.Group) o;
        return getName().equals(group.getName()) &&
                getRoom().equals(group.getRoom()) &&
                traineeList.equals(group.getTrainees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRoom(), traineeList);
    }
}


// экземпляры этого класса полностью потокобезопасны
class School {
    private String name;
    private int year;
    // Group потокобезопасный класс, но сет из него надо тоже обезапасить для вызовов из потоков
    private SortedSet<Group> groupSet = Collections.synchronizedSortedSet(new TreeSet<>(Comparator.comparing(Group::getName)));

    public School(String name, int year) throws TrainingException{
        setName(name);
        this.year = year;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) throws TrainingException {
        if (name==null || name.isEmpty())
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        this.name = name;
    }

    public synchronized int getYear() {
        return year;
    }

    public synchronized void setYear(int year) {
        this.year = year;
    }

    public synchronized Set<Group> getGroups() {
        return groupSet;
    }

    public synchronized void addGroup(Group group) throws TrainingException {
        if (!groupSet.add(group)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    public void removeGroup(Group group) throws TrainingException {
        if (!groupSet.remove(group)) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public synchronized void removeGroup(String name) throws TrainingException {
        if (!groupSet.removeIf((group -> group.getName().equals(name)))) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public synchronized boolean containsGroup(Group group) {
        return groupSet.contains(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof net.thumbtack.school.ttschool.School)) return false;
        net.thumbtack.school.ttschool.School school = (net.thumbtack.school.ttschool.School) o;
        return getYear() == school.getYear() &&
                name.equals(school.getName()) &&
                groupSet.equals(school.getGroups());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, getYear(), groupSet);
    }
}

public class Task9 {

}
