package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final File fileSouvenirs = new File("C:\\Users\\User\\IdeaProjects\\Souvenirs\\souvenirs.json");
    static final File fileManufacturers = new File("C:\\Users\\User\\IdeaProjects\\Souvenirs\\manufacturers.json");
    public static void main(String[] args) throws IOException {
        Manufacturer manufacturer0 = new Manufacturer("Souvenir World", "Ukraine");
        Manufacturer manufacturer1 = new Manufacturer("Disney Company", "USA");
        List<Manufacturer> manufacturerList = new ArrayList<>();
        manufacturerList.add(manufacturer0);
        manufacturerList.add(manufacturer1);
        System.out.println(manufacturerList);
        Json.listManufacturersToJsonFile(fileManufacturers,  manufacturerList);

        Souvenir souvenir0 = new Souvenir("Heart for St.Valentine Day", 7.0, LocalDate.of(2021, 1, 1), manufacturer0);
        Souvenir souvenir1= new Souvenir("Dart Weider", 12.0, LocalDate.of(2022, 1, 1), manufacturer1);
        Souvenir souvenir2= new Souvenir("Dart Weider", 17.0, LocalDate.of(2022, 1, 1), manufacturer0);

        List<Souvenir> souvenirList = new ArrayList<>();
        souvenirList.add(souvenir0);
        souvenirList.add(souvenir1);
        souvenirList.add(souvenir2);
        System.out.println(souvenirList);
        Json.listSouvenirsToJsonFile(fileSouvenirs, souvenirList);

        choiceOfAction(fileSouvenirs, fileManufacturers);

    }

    private static void choiceOfAction(File fileSouvenirs, File fileManufacturers) throws IOException {
        System.out.println("""
                Укажите номер желаемого действия:
                 1.  Добавить сувенир;
                 2.  Просмотр / редактирование / удаление сувениров;
                 3.  Добавить производителя;
                 4.  Просмотр / редактирование / удаление производителей;
                 5.  Просмотр сувениров заданного производителя;
                 6.  Просмотр сувениров из заданной страны;
                 7.  Найти производителей, имеющих сувениры дешевле заданной цены;
                 8.  Вывести сортированный по производителям список сувениров;
                 9.  Вывести сортированный по годам список сувениров;
                 10. Вывести производителей заданного сувенира, произведённого в заданном году;
                 0.  Выход""");

        switch (checkOfChoiceOfAction()) {
            case 1:
                makeSouvenir(fileSouvenirs, fileManufacturers); break;
            case 2:
                viewAndEditOrDeleteSouvenir(fileSouvenirs, fileManufacturers); break;
            case 3:
                makeManufacturer(fileSouvenirs, fileManufacturers); break;
            case 4:
                viewAndEditOrDeleteManufacturer(fileSouvenirs, fileManufacturers); break;
            case 5:
                viewSouvenirsOfManufacturer(fileSouvenirs, fileManufacturers); break;
            case 6:
                viewSouvenirsOfCountry(fileSouvenirs, fileManufacturers); break;
            case 7:
                viewManufacturersWithLowestPrice(fileSouvenirs, fileManufacturers); break;
            case 8:
                sortByManufacturersListOfSouvenirs(fileSouvenirs, fileManufacturers); break;
            case 9:
                sortByYearsListOfSouvenirs(fileSouvenirs, fileManufacturers); break;
            case 10:
                findManufacturerOfSameSouvenirInSameYear(fileSouvenirs, fileManufacturers); break;
            case 0:
                break;
        }
    }

    private static int checkOfChoiceOfAction() {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int number = scan.nextInt();
            if (number < 11 & number >= 0){
                return number;
            }
            else {System.out.println("Введите цифры от 1 до 11 или ноль для выхода");
                return checkOfChoiceOfAction();}

        }
        else {System.out.println("Введите цифры от 1 до 11 или ноль для выхода");
            return checkOfChoiceOfAction();}
    }

    private static void makeSouvenir(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Добавление сувенира");
        System.out.println("Введите название сувенира:");
        String souvenirName = inputString();
        System.out.println("Введите цену сувенира:");
        double souvenirPrice = inputPrice();
        System.out.println("Введите дату изготовления:");
        LocalDate localDate = inputDate();
        System.out.println("Выберите производителя:");
        ManufacturerInterface manufacturer = choiceOfManufacturer(fileManufacturers);
        System.out.println("Вы выбрали производителя: " + manufacturer);

        Souvenir souvenir = new Souvenir(souvenirName, souvenirPrice, localDate, manufacturer);
        souvenirList.add(souvenir);
        System.out.println("Добавлен " + souvenir);
        System.out.println("Список сувениров:");
        for (Object s : souvenirList) {
            System.out.println(s);
        }
        Json.listSouvenirsToJsonFile(fileSouvenirs, souvenirList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");

        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void viewAndEditOrDeleteSouvenir(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        int souvenirID = choiceOfSouvenir(fileSouvenirs);
        System.out.println("Вы выбрали: " + souvenirList.get(souvenirID));
        choiceOfActionWithSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
    }

    private static void makeManufacturer(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        System.out.println("Добавление производителя");
        System.out.println("Введите название производителя:");
        String name = inputString();
        System.out.println("Введите страну производителя:");
        String country = inputString();

        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturerList.add(manufacturer);
        System.out.println("Добавлен " + manufacturer);
        System.out.println("Список производителей:");
        for (Object s : manufacturerList) {
            System.out.println(s);
        }
        Json.listManufacturersToJsonFile(fileManufacturers, manufacturerList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void viewAndEditOrDeleteManufacturer(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        int manufacturerID = choiceOfManufacturerID(fileManufacturers);
        System.out.println("Вы выбрали: " + manufacturerList.get(manufacturerID));
        choiceOfActionWithManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
    }

    private static void choiceOfActionWithManufacturer(int manufacturerID, File fileSouvenirs, File fileManufacturers) throws IOException {

        switch (checkOfChoiceActionViewEditDelete()) {
            case 1 -> viewOfManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 2 -> editOfManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 3 -> deleteManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 0 -> choiceOfAction(fileSouvenirs, fileManufacturers);
            default -> throw new IllegalStateException("Unexpected value: " + checkOfChoiceActionViewEditDelete());
        }
    }

    private static void deleteManufacturer(int manufacturerID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("При удалении производителя все произведённые им сувениры будут также удалены. Вы уверены, что хотите удалить производителя? Y/N");
        if(yesOrNo()) {
            ManufacturerInterface manufacturer = manufacturerList.get(manufacturerID);
            System.out.println("Удалены следующие сувениры:");
            Iterator<Souvenir> souvenirIterator = souvenirList.iterator();
            while (souvenirIterator.hasNext()){
                Souvenir nextSouvenir = souvenirIterator.next();
            if(Objects.equals(nextSouvenir.getManufacturer(), manufacturer)) {
                System.out.println(nextSouvenir);
                souvenirIterator.remove();
                }
            }
            for (Souvenir souvenir : souvenirList) {
                if(Objects.equals(souvenir.getManufacturer(), manufacturer)) {
                    System.out.println(souvenir);
                }
            }
            souvenirList.removeIf(souvenir -> souvenir.getManufacturer().equals(manufacturer)); //так симпатичнее

            Json.listSouvenirsToJsonFile(fileSouvenirs, souvenirList);
            System.out.println("Удален производитель:  " + manufacturerList.get(manufacturerID));
            manufacturerList.remove(manufacturerID);
            Json.listManufacturersToJsonFile(fileManufacturers,manufacturerList);

        }
        choiceOfAction(fileSouvenirs, fileManufacturers);
    }

    private static void viewOfManufacturer(int manufacturerID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        System.out.println(manufacturerList.get(manufacturerID));

        switch (checkOfChoiceEditOrDelete()) {
            case 1 -> editOfManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 2 -> deleteManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 0 -> choiceOfAction(fileSouvenirs, fileManufacturers);
            default -> throw new IllegalStateException("Unexpected value: " + checkOfChoiceEditOrDelete());
        }
    }

    private static void editOfManufacturer(int manufacturerID, File fileSouvenirs, File fileManufacturers) throws IOException {
        System.out.println("""
                Что вы хотите редактировать?
                 1. Название;
                 2. Страну;
                 0. Bыход;
                """);

        switch (checkOfChoiceOfEditMenuOfManufacturer()) {
            case 1 -> editNameOfManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 2 -> editCountryOfManufacturer(manufacturerID, fileSouvenirs, fileManufacturers);
            case 0 -> choiceOfAction(fileSouvenirs, fileManufacturers);
            default -> throw new IllegalStateException("Unexpected value: " + checkOfChoiceOfEditMenuOfManufacturer());
        }
    }

    private static void editNameOfManufacturer(int manufacturerID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        System.out.println("Редактирование имени производителя");
        System.out.println("Введите новое название:");
        String newName = inputString();
        System.out.println("Производитель - " + manufacturerList.get(manufacturerID));
        manufacturerList.get(manufacturerID).setName(newName);
        System.out.println("был изменен на: " + manufacturerList.get(manufacturerID));
        Json.listManufacturersToJsonFile(fileManufacturers,manufacturerList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void editCountryOfManufacturer(int manufacturerID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        System.out.println("Редактирование страны производителя");
        System.out.println("Введите новое название:");
        String newCountry = inputString();
        System.out.println("Производитель - " + manufacturerList.get(manufacturerID));
        manufacturerList.get(manufacturerID).setCountry(newCountry);
        System.out.println("был изменен на: " + manufacturerList.get(manufacturerID));
        Json.listManufacturersToJsonFile(fileManufacturers,manufacturerList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static int checkOfChoiceOfEditMenuOfManufacturer() {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int number = scan.nextInt();
            if (number < 3 & number >= 0){
                return number;
            }
            else {System.out.println("Введите цифры от 1 до 2 или 0 для выхода");
                return checkOfChoiceOfEditMenuOfManufacturer();}
        }
        else {System.out.println("Введите цифры от 1 до 2 или 0 для выхода");
            return checkOfChoiceOfEditMenuOfManufacturer();}
    }


    private static void viewSouvenirsOfManufacturer(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Выберите сувениры какого производителя вас интересуют: ");
        ManufacturerInterface manufacturer = choiceOfManufacturer(fileManufacturers);
        System.out.println("Список сувениров производителя '" + manufacturer + "':");
        for (Souvenir souvenir : souvenirList) {
            if (souvenir.getManufacturer().equals(manufacturer)) {
                System.out.print(souvenirList.indexOf(souvenir) + ". ");
                System.out.println(souvenir);
            }
        }
        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void viewSouvenirsOfCountry(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Выберите сувениры какой страны вас интересуют: ");
        String country = inputString();
        System.out.println("Список сувениров, произведенных в '" + country + "':");
        for (Souvenir souvenir : souvenirList) {
            if (Objects.equals(souvenir.getManufacturer().getCountry(), country)) {
                System.out.print(souvenirList.indexOf(souvenir) + ". ");
                System.out.println(souvenir);
            }
        }
        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void viewManufacturersWithLowestPrice(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Введите заданную цену:");
        double givenPrice = inputPrice();
        Set<ManufacturerInterface> manufacturersSet;
        System.out.println("Список производителей, имеющих сувениры дешевле '" + givenPrice + "':");

        //Вариамт без стрима:
//        for (Souvenir souvenir : souvenirList) {
//                if (souvenir.getSouvenirPrice() < givenPrice){
//                manufacturersSet.add(souvenir.getManufacturer());
//            }
//        }

        //Вариант со стримом:

        manufacturersSet = souvenirList.stream().filter(souvenir -> souvenir.getSouvenirPrice() < givenPrice)
                .map(Souvenir::getManufacturer).collect(Collectors.toSet());

        for (ManufacturerInterface manufacturer : manufacturersSet) {
            System.out.println(manufacturer);
        }

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void sortByManufacturersListOfSouvenirs(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);

        //Вариант без стримов:

//        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
//        for (Manufacturer manufacturer : manufacturerList) {
//            System.out.println(manufacturer);
//            for (Souvenir souvenir : souvenirList) {
//                if (Objects.equals(souvenir.getManufacturer(), manufacturer)) {
//                    System.out.print("    ---");
//                    System.out.println(souvenir);
//                }
//            }
//        }

        //Вариант со стримом: (не уверен какой мне больше нравиться. Со стримом, конечно красиво, но с учётом красивого,
        // читабельного вывода, код получается не меньше)
        Map<ManufacturerInterface, List<Souvenir>> souvenirMapSortedByManufacturer = souvenirList.stream()
                .collect(Collectors.groupingBy(Souvenir::getManufacturer));
        for (ManufacturerInterface manufacturerInterface : souvenirMapSortedByManufacturer.keySet()) {
            List<Souvenir> s = souvenirMapSortedByManufacturer.get(manufacturerInterface);
            System.out.println(manufacturerInterface);
            for (Souvenir souvenir : s) {
                System.out.println("----- " + souvenir);
            }
        }

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void sortByYearsListOfSouvenirs(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);

        //Вариант без стримов:

//        HashSet<LocalDate> years = new HashSet<>();
//        for (Souvenir souvenir : souvenirList) {
//            years.add(souvenir.getDateOfManufacturing());
//        }
//        for (LocalDate year : years) {
//            System.out.println(year);
//            for (Souvenir souvenir : souvenirList) {
//                if (Objects.equals(souvenir.getDateOfManufacturing(), year)) {
//                    System.out.print("    ---");
//                    System.out.println(souvenir);
//                }
//            }
//        }

        // Вариант со стримом:

        Map<LocalDate, List<Souvenir>> souvenirMapSortedByLocalDate = souvenirList.stream()
                .collect(Collectors.groupingBy(Souvenir::getDateOfManufacturing));
        for (LocalDate localDate : souvenirMapSortedByLocalDate.keySet()) {
            List<Souvenir> s = souvenirMapSortedByLocalDate.get(localDate);
            System.out.println(localDate);
            for (Souvenir souvenir : s) {
                System.out.println("---- " + souvenir);
            }
        }

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void findManufacturerOfSameSouvenirInSameYear(File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Введите название сувенира:");
        String nameForSearch = inputString();
        System.out.println("Введите год выпуска сувенира:");
        LocalDate dateForSearch = inputDate();
        HashSet<ManufacturerInterface> manufacturersSet = new HashSet<>();
        System.out.println("Список производителей, выпускавших '" + nameForSearch + "' в " + dateForSearch +" году:");
        for (Souvenir souvenir : souvenirList) {
            if (Objects.equals(souvenir.getSouvenirName(), nameForSearch) && Objects.equals(souvenir.getDateOfManufacturing(), dateForSearch)){
                manufacturersSet.add(souvenir.getManufacturer());
            }
        }
        for (ManufacturerInterface manufacturer : manufacturersSet) {
            System.out.println(manufacturer);
        }
        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }
    private static int choiceOfManufacturerID(File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        System.out.println("Выберите производителя из списка: ");
        int i = 0;
        for (Manufacturer manufacturer : manufacturerList) {
            System.out.print(i + ". ");
            System.out.println(manufacturer);
            i+=1;
        }
        System.out.print("Наберите номер производителя: ");
        System.out.println();
        return inputManufacturer(fileManufacturers);
    }

    private static boolean yesOrNo() {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNext("[y,Y]")) {
            return true;}
        else if (scan.hasNext("[n,N]")) {
            return false;

        } else {System.out.println("Выберите Y или N");
            return yesOrNo();}
    }

    private static String inputString() {
        Scanner scan = new Scanner(System.in);
        String s = null;
        if (scan.hasNextLine()) {
            s = scan.nextLine();
        } else {
            System.out.println("Введите название в виде строки");
        }
        return s;
    }

    public static LocalDate inputDate() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите год: ");
        int year = scan.nextInt();
        return LocalDate.of(year, 1,1);
    }

    private static int inputInt() {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            return scan.nextInt();
        }
        else {System.out.println("Введите целое число:");
            return inputInt();}
    }

    private static double inputPrice() {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextDouble()) {
            return scan.nextDouble();
        }
        else {System.out.println("Введите число с дробной запятой:");
            return inputPrice();}
    }

    private static ManufacturerInterface choiceOfManufacturer(File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        int i = 0;
        for (Object manufacturer : manufacturerList) {
            System.out.print(i + ". ");
            System.out.println(manufacturer);
            i+=1;
        }
        System.out.print("Наберите номер производителя из списка: ");
        System.out.println();
        return manufacturerList.get(inputManufacturer(fileManufacturers));
    }

    private static int inputManufacturer(File fileManufacturers) throws IOException {
        List<Manufacturer> manufacturerList = Json.fileToListOfManufacturers(fileManufacturers);
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int i = scan.nextInt();
            if(i<manufacturerList.size()){
                return i;}
            else {System.out.println("Введите цифры от 0 до " + (manufacturerList.size()-1));
                return inputManufacturer(fileManufacturers);}
        }
        else {System.out.println("Введите цифры от 0 до " + (manufacturerList.size()-1));
            return inputManufacturer(fileManufacturers);}
    }

    private static int choiceOfSouvenir(File fileSouvenirs) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Выберите сувенир из списка: ");
        int i = 0;
        for (Souvenir souvenir : souvenirList) {
            System.out.print(i + ". ");
            System.out.println("'" + souvenir.getSouvenirName() + "' , " + souvenir.getManufacturer());
            i+=1;
        }
        System.out.print("Наберите номер сувенира: ");
        System.out.println();
        return inputSouvenir(souvenirList);
    }

    private static int inputSouvenir(List<Souvenir> souvenirList) {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int i = scan.nextInt();
            if(i<souvenirList.size()){
                return i;}
            else {System.out.println("Введите цифры от 0 до " + (souvenirList.size()-1));
                return inputSouvenir(souvenirList);}
        }
        else {System.out.println("Введите цифры от 0 до " + (souvenirList.size()-1));
            return inputSouvenir(souvenirList);}
    }

    private static void choiceOfActionWithSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {

         switch (checkOfChoiceActionViewEditDelete()) {
            case 1 -> viewOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 2 -> editOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 3 -> deleteSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 0 -> choiceOfAction(fileSouvenirs, fileManufacturers);
            default -> throw new IllegalStateException("Unexpected value: " + checkOfChoiceActionViewEditDelete());
        }
    }

    private static void deleteSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Вы уверены, что хотите удалить сувенир? Y/N");
        if(yesOrNo()) {
            System.out.println("Удален сувенир:  " + souvenirList.get(souvenirID));
            souvenirList.remove(souvenirID);
            Json.listSouvenirsToJsonFile(fileSouvenirs, souvenirList);
        }
        choiceOfAction(fileSouvenirs, fileManufacturers);
    }

    private static void editOfSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        System.out.println("""
                Что вы хотите редактировать?
                 1. Название;
                 2. Цену;
                 3. Дату изготовления;
                 4. Производителя;
                 0. Bыход;
                """);

        switch (checkOfChoiceOfEditMenuOfSouvenir()) {
            case 1 -> editNameOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 2 -> editPriceOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 3 -> editDateOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 4 -> editManufacturerOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 0 -> choiceOfAction(fileSouvenirs, fileManufacturers);
            default -> throw new IllegalStateException("Unexpected value: " + checkOfChoiceActionViewEditDelete());
        }
    }

    private static void editManufacturerOfSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Редактирование производителя");
        System.out.println("Выберите нового производителя из списка:");
        ManufacturerInterface newManufacturer = choiceOfManufacturer(fileManufacturers);
        System.out.println("Сувенир  -  " + souvenirList.get(souvenirID));
        souvenirList.get(souvenirID).setManufacturer(newManufacturer);
        System.out.println("изменен на: " + souvenirList.get(souvenirID));
        Json.listSouvenirsToJsonFile(fileSouvenirs,souvenirList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void editDateOfSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Редактирование даты производства");
        System.out.println("Введите новую дату:");
        LocalDate newDate = inputDate();
        System.out.println("Сувенир  -  " + souvenirList.get(souvenirID));
        souvenirList.get(souvenirID).setDateOfManufacturing(newDate);
        System.out.println("изменен на: " + souvenirList.get(souvenirID));
        Json.listSouvenirsToJsonFile(fileSouvenirs,souvenirList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void editPriceOfSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Редактирование цены");
        System.out.println("Введите новую цену:");
        int newPrice = inputInt();
        System.out.println("Сувенир  -  " + souvenirList.get(souvenirID));
        souvenirList.get(souvenirID).setSouvenirPrice(newPrice);
        System.out.println("изменен на: " + souvenirList.get(souvenirID));
        Json.listSouvenirsToJsonFile(fileSouvenirs,souvenirList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static void editNameOfSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println("Редактирование имени");
        System.out.println("Введите новое название:");
        String newName = inputString();
        System.out.println("Сувенир  -  " + souvenirList.get(souvenirID));
        souvenirList.get(souvenirID).setSouvenirName(newName);
        System.out.println("изменен на: " + souvenirList.get(souvenirID));
        Json.listSouvenirsToJsonFile(fileSouvenirs,souvenirList);

        System.out.println("\nЖелаете выполнить ещё что-то? Y/N");
        if(yesOrNo()) {
            choiceOfAction(fileSouvenirs, fileManufacturers);
        }
    }

    private static int checkOfChoiceOfEditMenuOfSouvenir() {
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int number = scan.nextInt();
            if (number < 5 & number >= 0){
                return number;
            }
            else {System.out.println("Введите цифры от 1 до 4 или 0 для выхода");
                return checkOfChoiceActionViewEditDelete();}
        }
        else {System.out.println("Введите цифры от 1 до 4 или 0 для выхода");
            return checkOfChoiceActionViewEditDelete();}
    }

    private static int checkOfChoiceEditOrDelete() {
        System.out.println("""
                Желаете отредактировать - наберите '1'
                Желаете удалить - наберите '2'
                Для выхода - наберите '0'""");
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int number = scan.nextInt();
            if (number < 3 & number >= 0){
                return number;
            }
            else {System.out.println("Введите цифры от 1 до 2 или 0 для выхода");
                return checkOfChoiceEditOrDelete();}
        }
        else {System.out.println("Введите цифры от 1 до 2 или 0 для выхода");
            return checkOfChoiceEditOrDelete();}
    }

    private static int checkOfChoiceActionViewEditDelete() {
        System.out.println("""
                Что вы хотите сделать?
                 1. Просмотреть;
                 2. Редактировать;
                 3. Удалить;
                 0. Bыход;
                """);
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            int number = scan.nextInt();
            if (number < 4 & number >= 0){
                return number;
            }
            else {System.out.println("Введите цифры от 1 до 3 или 0 для выхода");
                return checkOfChoiceActionViewEditDelete();}
        }
        else {System.out.println("Введите цифры от 1 до 3 или 0 для выхода");
            return checkOfChoiceActionViewEditDelete();}
    }

    private static void viewOfSouvenir(int souvenirID, File fileSouvenirs, File fileManufacturers) throws IOException {
        List<Souvenir> souvenirList = Json.fileToListOfSouvenirs(fileSouvenirs);
        System.out.println(souvenirList.get(souvenirID));
        System.out.println("""
                Желаете отредактировать - наберите '1'
                Желаете удалить - наберите '2'
                Для выхода - наберите '0'""");
        switch (checkOfChoiceEditOrDelete()) {
            case 1 -> editOfSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 2 -> deleteSouvenir(souvenirID, fileSouvenirs, fileManufacturers);
            case 0 -> choiceOfAction(fileSouvenirs, fileManufacturers);
            default -> throw new IllegalStateException("Unexpected value: " + checkOfChoiceEditOrDelete());
        }
    }
}