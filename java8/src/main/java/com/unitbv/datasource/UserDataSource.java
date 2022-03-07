package com.unitbv.datasource;

import com.unitbv.model.User;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDataSource {

    private List<User> users = new ArrayList<>();

    public UserDataSource(){
        users.add(new User(1, "John", "Wick", 35, "actor"));
        users.add(new User(2, "Jayce", "Lucas", 35, "driver"));
        users.add(new User(3, "Jack", "Spades", 18, "gamer"));
        users.add(new User(4, "Doug", "Rain", 55, "chef"));
        users.add(new User(5, "Lena", "Sunday", 12, "student"));
        users.add(new User(6, "Missy", "Cooper", 23, "actor"));
        users.add(new User(7, "Mark", "John", 17, "student"));
    }

    public UserDataSource(List<User> users){
        this.users = users;
    }

    public List<User> getAll(){
        return users;
    }

    public Optional<User> findById(int id){
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user;
    }

    public String getUsersNamesWithAgeGreaterThanThirty(){
        String text = users.stream()
                .filter(u -> u.getAge() > 30)
                .map(User::getFirstName)
                .collect(Collectors.joining(" - "));
        return text;
    }

    public int sumUpUserAgesWhereFirstNameStartsWithJ(){
        return users.stream()
                .filter(u -> u.getFirstName().startsWith("J"))
                .mapToInt(User::getAge)
                .reduce(0, Integer::sum);
    }

    public User getUserWithHighestAge(){
        return users.stream()
                .sorted((u1, u2) -> Integer.compare(u2.getAge(), u1.getAge()))
                .findFirst()
                .orElse(new User());
    }

    public User getUserWithHighestAge_V2(){
        return users.stream()
                .max(Comparator.comparingInt(User::getAge))
                .orElse(new User());
    }

    public List<User> mergeUserLists(List<User> l1, List<User> l2){
        return Stream.of(l1, l2)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // <---------- TO DO ---------->

    // Get the full names for all users
    public List<String> getFullNames(){
        List<String> names=new ArrayList<String>();

        for(int i=0;i<users.size();i++)
        {
            String fullName=users.get(i).getFirstName()+" "+users.get(i).getLastName();
            names.add(fullName);
        }
        return names;
    }

    // Get the job of the oldest user
    public String getJobOfTheOldestUser(){
        int max=0;
        String job=new String();
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getAge()>max)
            {
                max=users.get(i).getAge();
                job=users.get(i).getJob();
            }
        }
        return job;
    }

    // Get user (distinct) jobs sorted alphabetically
    public Set<String> getAllUserJobsSorted(){
        List<String> sortedJobs= new ArrayList<>();
        Set<String> setSortedJobs=new HashSet<>();
        for(int i=0;i<users.size();i++)
        {
            sortedJobs.add(users.get(i).getJob());

        }
        Collections.sort(sortedJobs);
        for(int i=0;i<sortedJobs.size();i++)
        {
            setSortedJobs.add(sortedJobs.get(i));
        }

        return setSortedJobs;
    }

    // Find user by first name - throw RuntimeException if not found
    public User findByFirstName(String firstName) throws RuntimeException {
        int ok = 0;
        User user=new User();
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getFirstName() == firstName) {
                    user = users.get(i);
                    ok = 1;
                }
            }
            if (ok == 0) {
                throw new RuntimeException();
            }
            else
            {
                return user;
            }
        } catch (RuntimeException e) {
            System.out.println("RuntimeException thrown");
        }

        return user;
    }

    // Check if all users are older than the specified age
    public boolean areAllUsersOlderThan(int age){
        // your code here - please try with allMatch/noneMatch
        boolean result = false;
        for (int i = 0; i < users.size(); i++)
        {
            int finalI = i;
            result = users.stream().allMatch(areThey->users.get(finalI).getAge()>age);
        }
        return result;
    }

    // Add a new user - if there is a user with the same id, don't add and throw a RuntimeException
    public void addUser(User user){
        Optional<User> user1 = Optional.ofNullable(user);
        user1.ifPresent(theUser ->new User());

        user1.orElseThrow(()->new RuntimeException());
    }

    // For all students (user.job = "student"), change the job to "graduate" and add 5 years to their age
    public void changeAllStudentsJobsAndAges(){
        for (int i = 0; i < users.size(); i++)
        {
            if(users.get(i).getJob()=="student")
            {
                users.set(i,users.get(i)).setJob("graduate");
                users.set(i,users.get(i)).setAge(users.get(i).getAge()+5);
            }
        }
    }

    // Count users that have the given Job
    public long countUsersHavingTheSpecifiedJob(String job){
        long count=0;
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getJob()==job)
            {
                count++;
            }
        }
        return count;
    }

    // Get a map where the key is the user id and the value is the User object itself
    public Map<Integer, User> getMapOfUsers(){
        Map<Integer, User> map=new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            map.put(users.get(i).getId(),users.get(i));
        }
        return map;
    }

    // Get a predicate for filtering by the given name - applies to both firstName and lastName
    public Predicate<User> getPredicateForFilteringByName(String name){
        // your code here
        return null;
    }

    // Get a comparator for User type - compare by age ascending, then by job alphabetically
    public Comparator<User> getUserComparator(){
        // your code here
        return null;
    }

    // Filter users using the given Predicate
    public List<User> filterUsers(Predicate<? super User> predicate){
        return users.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    // Sort users using the given Comparator
    public List<User> sortUsers(Comparator<? super User> comparator){
        return users.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
