package mft.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import mft.model.bl.PersonBl;
import mft.model.entity.Person;
import mft.model.entity.enums.City;
import mft.model.entity.enums.Gender;
import mft.model.tools.Validator;
import mft.view.WindowsManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class ProfileController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, familyTxt, searchFamilyTxt;

    @FXML
    private DatePicker birthDate;

    @FXML
    private ComboBox<String> cityCmb;

    @FXML
    private RadioButton maleRdo, femaleRdo;

    @FXML
    private ToggleGroup genderToggle;

    @FXML
    private CheckBox algoChk, seChk, eeChk;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private TableView<Person> personTbl;

    @FXML
    private TableColumn<Person, Integer> idCol;

    @FXML
    private TableColumn<Person, String> firstNameCol, lastNameCol;

    @FXML
    private MenuItem closeMnu, newMnu, aboutMnu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("App Started");
        for (City value : City.values()) {
            cityCmb.getItems().add(value.name());
        }

        try {
            resetForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Load Error\n" + e.getMessage());
            alert.show();
        }

        newMnu.setOnAction(event -> {
            try {
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Load Error\n" + e.getMessage());
                alert.show();
            }
        });

        closeMnu.setOnAction((event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?");
            if (alert.showAndWait().get().equals(ButtonType.OK)) {
                Platform.exit();
            }
            log.info("App Closed");
        }));

        aboutMnu.setOnAction((event) -> {
            try {
                WindowsManager.showAboutForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Load Error\n" + e.getMessage());
                alert.show();
            }
        });

        saveBtn.setOnAction(event -> {
            try {
                RadioButton gender = (RadioButton) genderToggle.getSelectedToggle();

                Person person = Person
                        .builder()
                        .name(Validator.nameValidator(nameTxt.getText(), "Invalid Name"))
                        .family(Validator.nameValidator(familyTxt.getText(), "Invalid Family"))
                        .gender(Gender.valueOf(gender.getText()))
                        .birthDate(birthDate.getValue())
                        .city(City.valueOf(cityCmb.getSelectionModel().getSelectedItem()))
                        .algorithmSkill(algoChk.isSelected())
                        .JavaSESkill(seChk.isSelected())
                        .JavaEESkill(eeChk.isSelected())
                        .build();
                PersonBl.getPersonBl().save(person);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, " Person Saved\n" + person);
                alert.show();
                resetForm();
                log.info("Person Saved " + person);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Person Save Error\n" + e.getMessage());
                alert.show();
                log.error("Save Error : " + e.getMessage());
            }
        });

        editBtn.setOnAction(event -> {
            try {
                RadioButton gender = (RadioButton) genderToggle.getSelectedToggle();

                Person person = Person
                        .builder()
                        .id(Integer.parseInt(idTxt.getText()))
                        .name(Validator.nameValidator(nameTxt.getText(), "Invalid Name"))
                        .family(Validator.nameValidator(familyTxt.getText(), "Invalid Family"))
                        .gender(Gender.valueOf(gender.getText()))
                        .birthDate(birthDate.getValue())
                        .city(City.valueOf(cityCmb.getSelectionModel().getSelectedItem()))
                        .algorithmSkill(algoChk.isSelected())
                        .JavaSESkill(seChk.isSelected())
                        .JavaEESkill(eeChk.isSelected())
                        .build();
                PersonBl.getPersonBl().edit(person);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, " Person Edited\n" + person);
                alert.show();
                resetForm();
                log.info("Person Edited " + person);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Person Edit Error\n" + e.getMessage());
                alert.show();
                log.error("Edit Error : " + e.getMessage());
            }
        });

        removeBtn.setOnAction(event -> {
            try {
                PersonBl.getPersonBl().remove(Integer.parseInt(idTxt.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, " Person Removed\n" + idTxt.getText());
                alert.show();
                log.info("Person Removed " + idTxt.getText());
                resetForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Person Remove Error\n" + e.getMessage());
                alert.show();
                log.error("Remove Error : " + e.getMessage());
            }
        });

        searchFamilyTxt.setOnKeyReleased((event) -> {
            try {
                showDataOnTable(PersonBl.getPersonBl().findByFamily(searchFamilyTxt.getText()));
                log.info("Person Searched By Family : " + searchFamilyTxt.getText());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Persons\n" + e.getMessage());
                alert.show();
                log.error("Find By Family Error : " + e.getMessage());
            }
        });

        personTbl.setOnMouseClicked((event) -> {
            Person person = personTbl.getSelectionModel().getSelectedItem();
            idTxt.setText(String.valueOf(person.getId()));
            nameTxt.setText(person.getName());
            familyTxt.setText(person.getFamily());
            if (person.getGender().equals(Gender.Male)) {
                maleRdo.setSelected(true);
            } else {
                femaleRdo.setSelected(true);
            }

            birthDate.setValue(person.getBirthDate());

            cityCmb.getSelectionModel().select(person.getCity().ordinal());

            algoChk.setSelected(person.isAlgorithmSkill());
            seChk.setSelected(person.isJavaSESkill());
            eeChk.setSelected(person.isJavaEESkill());
        });
    }

    private void showDataOnTable(List<Person> personList) {
        ObservableList<Person> observableList = FXCollections.observableList(personList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("family"));

        personTbl.setItems(observableList);
    }

    private void resetForm() throws Exception {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        maleRdo.setSelected(true);
        birthDate.setValue(null);
        cityCmb.getSelectionModel().select(0);
        algoChk.setSelected(false);
        seChk.setSelected(false);
        eeChk.setSelected(false);
        showDataOnTable(PersonBl.getPersonBl().findAll());
    }
}
