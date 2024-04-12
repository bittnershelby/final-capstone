package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Patient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class JdbcDoctorDao implements DoctorDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcDoctorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Doctor getDoctorByUserId (int userId) {
        Doctor doctor = new Doctor( ) ;
//        String sql =  " SELECT account_id, user_id, balance FROM account where  user_id = ?  ; ";
//
//        try {
//            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
//            if (rowSet.next()) {
//                doctor = mapRowToAccount(rowSet);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        }
        return doctor;
    }

    @Override
    public void createDoctor(Doctor doctor){

        String sql =
                " INSERT INTO doctor (doctor_id, first_name, last_name, specialization, cost_per_hour) " +
                        " VALUES ( ?, ?, ?, ?, ? )  RETURNING doctor_id ; " ;

        try {

            int newDoctorId =   jdbcTemplate.queryForObject( sql,
                    int.class,
                    doctor.getDoctorId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getSpecialization(),
                    doctor.getCostPerHour()
            );

            // doctor.setDoctorId(doctorId); // Set the generated doctorId to the newDoctor object


        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

    }
    private Doctor mapRowToDoctor(SqlRowSet rs) {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(  rs.getInt("doctor_id"));
        return doctor;
    }
}
