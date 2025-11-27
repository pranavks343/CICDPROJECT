import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axiosClient from '../api/axiosClient';

const DoctorVisitForm = () => {
  const { currentUser } = useAuth();
  const navigate = useNavigate();
  const [patients, setPatients] = useState([]);
  const [showNewPatientForm, setShowNewPatientForm] = useState(false);
  const [newPatientData, setNewPatientData] = useState({
    fullName: '',
    email: '',
    password: '',
    phoneNumber: '',
    gender: '',
    dateOfBirth: '',
    address: '',
  });
  const [formData, setFormData] = useState({
    patientId: '',
    visitDate: new Date().toISOString().slice(0, 16),
    reasonForVisit: '',
    symptoms: '',
    diagnosis: '',
    prescribedMedicines: '',
    heightCm: '',
    weightKg: '',
    bloodPressure: '',
    pulse: '',
    temperature: '',
    notes: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    try {
      const response = await axiosClient.get('/users?role=PATIENT');
      setPatients(response.data);
    } catch (error) {
      console.error('Error fetching patients:', error);
    }
  };

  const handleCreateNewPatient = async () => {
    setError('');
    setLoading(true);

    try {
      const response = await axiosClient.post('/users', {
        ...newPatientData,
        role: 'PATIENT',
      });
      
      // Add new patient to the list and select them
      await fetchPatients();
      setFormData({ ...formData, patientId: response.data.id });
      setShowNewPatientForm(false);
      setNewPatientData({
        fullName: '',
        email: '',
        password: '',
        phoneNumber: '',
        gender: '',
        dateOfBirth: '',
        address: '',
      });
      setError('');
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to create patient');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const visitData = {
        ...formData,
        doctorId: currentUser.id,
        patientId: parseInt(formData.patientId),
        visitDate: new Date(formData.visitDate).toISOString(),
        heightCm: formData.heightCm ? parseFloat(formData.heightCm) : null,
        weightKg: formData.weightKg ? parseFloat(formData.weightKg) : null,
        pulse: formData.pulse ? parseInt(formData.pulse) : null,
        temperature: formData.temperature ? parseFloat(formData.temperature) : null,
      };

      await axiosClient.post('/visits', visitData);
      navigate('/doctor');
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to create visit');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Create New Visit</h1>
      {error && <div className="error">{error}</div>}
      
      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '5px' }}>
              <label>Patient *</label>
              <button
                type="button"
                className="btn btn-primary"
                style={{ fontSize: '0.9em', padding: '5px 15px' }}
                onClick={() => setShowNewPatientForm(!showNewPatientForm)}
              >
                {showNewPatientForm ? 'Cancel' : '+ Add New Patient'}
              </button>
            </div>
            
            {!showNewPatientForm ? (
              <select
                value={formData.patientId}
                onChange={(e) => setFormData({ ...formData, patientId: e.target.value })}
                required
              >
                <option value="">Select Patient</option>
                {patients.map((patient) => (
                  <option key={patient.id} value={patient.id}>
                    {patient.fullName} ({patient.email})
                  </option>
                ))}
              </select>
            ) : (
              <div style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '5px', backgroundColor: '#f9f9f9' }}>
                <h3 style={{ marginTop: 0 }}>Create New Patient</h3>
                <div className="form-group">
                  <label>Full Name *</label>
                  <input
                    type="text"
                    value={newPatientData.fullName}
                    onChange={(e) => setNewPatientData({ ...newPatientData, fullName: e.target.value })}
                    placeholder="Enter patient name"
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Email *</label>
                  <input
                    type="email"
                    value={newPatientData.email}
                    onChange={(e) => setNewPatientData({ ...newPatientData, email: e.target.value })}
                    placeholder="patient@example.com"
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Password *</label>
                  <input
                    type="password"
                    value={newPatientData.password}
                    onChange={(e) => setNewPatientData({ ...newPatientData, password: e.target.value })}
                    placeholder="Minimum 6 characters"
                    required
                    minLength="6"
                  />
                </div>
                <div className="form-group">
                  <label>Phone Number</label>
                  <input
                    type="text"
                    value={newPatientData.phoneNumber}
                    onChange={(e) => setNewPatientData({ ...newPatientData, phoneNumber: e.target.value })}
                    placeholder="Phone number"
                  />
                </div>
                <div className="form-group">
                  <label>Gender</label>
                  <select
                    value={newPatientData.gender}
                    onChange={(e) => setNewPatientData({ ...newPatientData, gender: e.target.value })}
                  >
                    <option value="">Select Gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                  </select>
                </div>
                <div className="form-group">
                  <label>Date of Birth</label>
                  <input
                    type="date"
                    value={newPatientData.dateOfBirth}
                    onChange={(e) => setNewPatientData({ ...newPatientData, dateOfBirth: e.target.value })}
                  />
                </div>
                <div className="form-group">
                  <label>Address</label>
                  <textarea
                    value={newPatientData.address}
                    onChange={(e) => setNewPatientData({ ...newPatientData, address: e.target.value })}
                    placeholder="Patient address"
                  />
                </div>
                <button
                  type="button"
                  className="btn btn-success"
                  onClick={handleCreateNewPatient}
                  disabled={loading || !newPatientData.fullName || !newPatientData.email || !newPatientData.password}
                  style={{ width: '100%' }}
                >
                  {loading ? 'Creating Patient...' : 'Create Patient & Continue'}
                </button>
              </div>
            )}
          </div>

          <div className="form-group">
            <label>Visit Date & Time *</label>
            <input
              type="datetime-local"
              value={formData.visitDate}
              onChange={(e) => setFormData({ ...formData, visitDate: e.target.value })}
              required
            />
          </div>

          <div className="form-group">
            <label>Reason for Visit</label>
            <input
              type="text"
              value={formData.reasonForVisit}
              onChange={(e) => setFormData({ ...formData, reasonForVisit: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Symptoms</label>
            <textarea
              value={formData.symptoms}
              onChange={(e) => setFormData({ ...formData, symptoms: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Diagnosis</label>
            <textarea
              value={formData.diagnosis}
              onChange={(e) => setFormData({ ...formData, diagnosis: e.target.value })}
            />
          </div>

          <div className="form-group">
            <label>Prescribed Medicines</label>
            <textarea
              value={formData.prescribedMedicines}
              onChange={(e) => setFormData({ ...formData, prescribedMedicines: e.target.value })}
            />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>
            <div className="form-group">
              <label>Height (cm)</label>
              <input
                type="number"
                step="0.1"
                value={formData.heightCm}
                onChange={(e) => setFormData({ ...formData, heightCm: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label>Weight (kg)</label>
              <input
                type="number"
                step="0.1"
                value={formData.weightKg}
                onChange={(e) => setFormData({ ...formData, weightKg: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label>Blood Pressure</label>
              <input
                type="text"
                placeholder="e.g., 120/80"
                value={formData.bloodPressure}
                onChange={(e) => setFormData({ ...formData, bloodPressure: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label>Pulse (bpm)</label>
              <input
                type="number"
                value={formData.pulse}
                onChange={(e) => setFormData({ ...formData, pulse: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label>Temperature (°C)</label>
              <input
                type="number"
                step="0.1"
                value={formData.temperature}
                onChange={(e) => setFormData({ ...formData, temperature: e.target.value })}
              />
            </div>
          </div>

          <div className="form-group">
            <label>Notes</label>
            <textarea
              rows="4"
              value={formData.notes}
              onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
            />
          </div>

          <div style={{ display: 'flex', gap: '10px' }}>
            <button type="submit" className="btn btn-success" disabled={loading}>
              {loading ? 'Creating...' : 'Create Visit'}
            </button>
            <button
              type="button"
              className="btn btn-danger"
              onClick={() => navigate('/doctor')}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default DoctorVisitForm;

