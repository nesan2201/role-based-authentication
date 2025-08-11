import React, { useState } from 'react';
import api from '../api';
import { useNavigate } from 'react-router-dom';

export default function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const nav = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/auth/register', { name, email, password });
      alert('Registered, login now');
      nav('/login');
    } catch (err) {
      alert(err?.response?.data || 'Register failed');
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ width: '100%', maxWidth: '400px' }}>
        <h3 className="text-center mb-4 text-success">Register (Employee)</h3>
        <form onSubmit={submit}>
          <input className="form-control mb-3" value={name} onChange={e => setName(e.target.value)} placeholder="Name" required />
          <input className="form-control mb-3" value={email} onChange={e => setEmail(e.target.value)} placeholder="Email" required />
          <input type="password" className="form-control mb-3" value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" required />
          <button className="btn btn-success w-100"><i className="fas fa-user-plus"></i> Register</button>
        </form>
      </div>
    </div>
  );
}
