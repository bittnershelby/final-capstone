package com.techelevator.controller;
import com.techelevator.dao.AppointmentDao;
import com.techelevator.model.Appointment;
import com.techelevator.model.Doctor;
import com.techelevator.model.Patient;
import com.techelevator.model.ScheduleBlock;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;


@PreAuthorize("permitAll")
@RestController
@CrossOrigin
public class AppointmentController {

    private AppointmentDao appointmentDao;

    public AppointmentController(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    // this is for patients to see their appointments
    @RequestMapping(path = "/patient-appointments/{patientId}", method = RequestMethod.GET)
    public List<Appointment> getAppointmentByPatientIdController(@PathVariable int patientId) {
        List<Appointment> result = new ArrayList<>();
        Patient patient;
        try {
            result = appointmentDao.getAppointmentByPatientId(patientId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    // this is for doctors to see their appointments
    @RequestMapping(path = "/doctor-appointments/{doctorId}", method = RequestMethod.GET)
    public List<Appointment> getAppointmentListByDoctorId(@PathVariable int doctorId) {
        List<Appointment> result = new ArrayList<>();
        Doctor doctor;
        try {
            result = appointmentDao.getAppointmentListByDoctorId(doctorId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/appointments", method = RequestMethod.POST)
    public void createAppointmentController(@RequestBody Appointment newAppointment) {
        try {
             appointmentDao.createAppointment(newAppointment);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }


    

    @RequestMapping(path = "/appointments/{date}/{doctorId}", method = RequestMethod.GET)
    public List<ScheduleBlock> getAppointmentAvailabilityDate(@PathVariable String date , @PathVariable  int doctorId){


        try {
            return appointmentDao.getAppointmentsForDate(date, doctorId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR );
        }


    }

}
