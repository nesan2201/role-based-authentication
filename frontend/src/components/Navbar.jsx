import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const nav = useNavigate();

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
      <div className="container">
        <Link className="navbar-brand text-primary fw-bold" to="/">
          <i className="fas fa-users me-2"></i> EmpUser
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse-nav me-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                <i className="fas fa-home me-1"></i> Home
              </Link>
            </li>
          

          <ul className="navbar-nav ms-auto">
            {!user && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/login">
                    <i className="fas fa-sign-in-alt me-1"></i> Login
                  </Link>
                                 <Link className="nav-link" to="/register">
                    <i className="fas fa-user-plus me-1"></i> Register
                  </Link>
                </li>
              </>
            )}

            {user && user.role === 'ROLE_ADMIN' && (
              <li className="nav-item">
                <Link className="nav-link" to="/admin">
                  <i className="fas fa-user-shield me-1"></i> Admin
                </Link>
              </li>
            )}

            {user && user.role === 'ROLE_EMPLOYEE' && (
              <li className="nav-item">
                <Link className="nav-link" to="/employee">
                  <i className="fas fa-briefcase me-1"></i> Employee
                </Link>
              </li>
            )}

            {user && (
              <li className="nav-item">
                <button
                  className="btn btn-outline-danger ms-2"
                  onClick={() => {
                    logout();
                    nav('/login');
                  }}
                >
                  <i className="fas fa-sign-out-alt me-1"></i> Logout
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}
