import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axiosClient from '../api/axiosClient';

const DoctorDashboard = () => {
  const { currentUser } = useAuth();
  const [visits, setVisits] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (currentUser) {
      fetchVisits();
    }
  }, [currentUser]);

  const fetchVisits = async () => {
    try {
      const response = await axiosClient.get(`/visits/doctor/${currentUser.id}`);
      setVisits(response.data);
    } catch (error) {
      console.error('Error fetching visits:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="container">Loading...</div>;
  }

  return (
    <div className="container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px' }}>
        <div>
          <h1>Doctor Dashboard</h1>
          <p>Welcome, Dr. {currentUser.fullName}</p>
        </div>
        <Link to="/doctor/new-visit" className="btn btn-primary">
          Add New Visit
        </Link>
      </div>

      <div className="card">
        <h2>My Visits</h2>
        {visits.length === 0 ? (
          <p>No visits found. Create your first visit!</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Patient</th>
                <th>Reason</th>
                <th>Diagnosis</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {visits.map((visit) => (
                <tr key={visit.id}>
                  <td>{new Date(visit.visitDate).toLocaleDateString()}</td>
                  <td>{visit.patientName}</td>
                  <td>{visit.reasonForVisit || 'N/A'}</td>
                  <td>{visit.diagnosis || 'N/A'}</td>
                  <td>
                    <Link to={`/doctor/visit/${visit.id}`} className="btn btn-primary" style={{ textDecoration: 'none', display: 'inline-block' }}>
                      View Details
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default DoctorDashboard;

