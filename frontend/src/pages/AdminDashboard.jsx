import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axiosClient from '../api/axiosClient';
import './AdminDashboard.css';

const AdminDashboard = () => {
  const [stats, setStats] = useState(null);
  const [doctors, setDoctors] = useState([]);
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [statsRes, doctorsRes, patientsRes] = await Promise.all([
        axiosClient.get('/admin/stats'),
        axiosClient.get('/users?role=DOCTOR'),
        axiosClient.get('/users?role=PATIENT'),
      ]);
      setStats(statsRes.data);
      setDoctors(doctorsRes.data);
      setPatients(patientsRes.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="container">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Admin Dashboard</h1>
      
      {stats && (
        <div className="stats-grid">
          <div className="stat-card">
            <h3>{stats.totalUsers}</h3>
            <p>Total Users</p>
          </div>
          <div className="stat-card">
            <h3>{stats.totalDoctors}</h3>
            <p>Total Doctors</p>
          </div>
          <div className="stat-card">
            <h3>{stats.totalPatients}</h3>
            <p>Total Patients</p>
          </div>
          <div className="stat-card">
            <h3>{stats.totalVisits}</h3>
            <p>Total Visits</p>
          </div>
        </div>
      )}

      <div className="dashboard-actions">
        <Link to="/admin/doctors" className="btn btn-primary">
          Manage Doctors
        </Link>
        <Link to="/admin/patients" className="btn btn-primary">
          Manage Patients
        </Link>
      </div>

      <div className="dashboard-section">
        <h2>Recent Doctors</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Specialization</th>
              <th>Phone</th>
            </tr>
          </thead>
          <tbody>
            {doctors.slice(0, 5).map((doctor) => (
              <tr key={doctor.id}>
                <td>{doctor.fullName}</td>
                <td>{doctor.email}</td>
                <td>{doctor.specialization || 'N/A'}</td>
                <td>{doctor.phoneNumber || 'N/A'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="dashboard-section">
        <h2>Recent Patients</h2>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Gender</th>
            </tr>
          </thead>
          <tbody>
            {patients.slice(0, 5).map((patient) => (
              <tr key={patient.id}>
                <td>{patient.fullName}</td>
                <td>{patient.email}</td>
                <td>{patient.phoneNumber || 'N/A'}</td>
                <td>{patient.gender || 'N/A'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminDashboard;

