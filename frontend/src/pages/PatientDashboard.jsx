import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import axiosClient from '../api/axiosClient';

const PatientDashboard = () => {
  const { currentUser } = useAuth();
  const [visits, setVisits] = useState([]);
  const [userInfo, setUserInfo] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (currentUser) {
      fetchData();
    }
  }, [currentUser]);

  const fetchData = async () => {
    try {
      const [visitsRes, userRes] = await Promise.all([
        axiosClient.get(`/visits/patient/${currentUser.id}`),
        axiosClient.get(`/users/${currentUser.id}`),
      ]);
      setVisits(visitsRes.data);
      setUserInfo(userRes.data);
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
      <h1>Patient Dashboard</h1>
      <p>Welcome, {currentUser.fullName}</p>

      {userInfo && (
        <div className="card">
          <h2>Profile Information</h2>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
            <div>
              <strong>Email:</strong> {userInfo.email}
            </div>
            <div>
              <strong>Phone:</strong> {userInfo.phoneNumber || 'N/A'}
            </div>
            <div>
              <strong>Gender:</strong> {userInfo.gender || 'N/A'}
            </div>
            <div>
              <strong>Date of Birth:</strong> {userInfo.dateOfBirth || 'N/A'}
            </div>
            <div style={{ gridColumn: '1 / -1' }}>
              <strong>Address:</strong> {userInfo.address || 'N/A'}
            </div>
          </div>
        </div>
      )}

      <div className="card">
        <h2>My Visits</h2>
        {visits.length === 0 ? (
          <p>No visits found.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Doctor</th>
                <th>Reason</th>
                <th>Diagnosis</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {visits.map((visit) => (
                <tr key={visit.id}>
                  <td>{new Date(visit.visitDate).toLocaleDateString()}</td>
                  <td>{visit.doctorName}</td>
                  <td>{visit.reasonForVisit || 'N/A'}</td>
                  <td>{visit.diagnosis || 'N/A'}</td>
                  <td>
                    <button
                      className="btn btn-primary"
                      onClick={() => {
                        alert(`Visit Details:\n\nDate: ${new Date(visit.visitDate).toLocaleString()}\nDoctor: ${visit.doctorName}\nReason: ${visit.reasonForVisit || 'N/A'}\nSymptoms: ${visit.symptoms || 'N/A'}\nDiagnosis: ${visit.diagnosis || 'N/A'}\nMedicines: ${visit.prescribedMedicines || 'N/A'}\nNotes: ${visit.notes || 'N/A'}`);
                      }}
                    >
                      View Details
                    </button>
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

export default PatientDashboard;

