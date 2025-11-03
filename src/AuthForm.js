import React, { useState } from "react";
import axios from "axios";

export default function AuthForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const register = async () => {
        await axios.post("http://localhost:8080/api/auth/register", null, {
            params: { email, password },
        });
        alert("Kiểm tra email để xác thực!");
    };

    const login = async () => {
        await axios.post("http://localhost:8080/api/auth/login", null, {
            params: { email, password },
        });
        alert("Đăng nhập thành công!");
    };

    return (
        <div style={{ padding: 20 }}>
            <h2>Đăng ký / Đăng nhập</h2>
            <input placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
            <input type="password" placeholder="Mật khẩu" onChange={(e) => setPassword(e.target.value)} />
            <button onClick={register}>Đăng ký</button>
            <button onClick={login}>Đăng nhập</button>
        </div>
    );
}
