package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.auth;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IAuthService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private final TKDoiTuongSuDungRepository tkDoiTuongSuDungRepository;
    private final DoiTuongSuDungRepository doiTuongSuDungRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(
            TKDoiTuongSuDungRepository tkRepo,
            DoiTuongSuDungRepository doiTuongRepo,
            EmailService emailService
    ) {
        this.tkDoiTuongSuDungRepository = tkRepo;
        this.doiTuongSuDungRepository = doiTuongRepo;
        this.emailService = emailService;
    }

    // Đăng ký tài khoản
    @Override
    public TKDoiTuongSuDung register(String hoTen, String email, String password, String role)
    {
        if (tkDoiTuongSuDungRepository.findByTenDangNhap(email).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // Tạo tài khoản
        TKDoiTuongSuDung tk = new TKDoiTuongSuDung();
        tk.setTenDangNhap(email);
        tk.setMatKhau(encoder.encode(password));
        tk.setTrangThaiKichHoat(false);

        tkDoiTuongSuDungRepository.save(tk);

        // Tạo đối tượng người dùng
        DoiTuongSuDung doiTuong = new DoiTuongSuDung();
        doiTuong.setHoTen(hoTen);
        doiTuong.setEmail(email);
        doiTuong.setLoaiDoiTuongSuDung(LoaiDoiTuongSuDung.valueOf(role.toUpperCase()));
        doiTuong.setTaiKhoan(tk);

        doiTuong.setTaiKhoan(tk);

        // B2: Lưu doiTuong để Mongo sinh _id
        doiTuongSuDungRepository.save(doiTuong);

        // B3: Gán ngược lại rồi update tk
        tk.setDoiTuongSuDung(doiTuong);
        tkDoiTuongSuDungRepository.save(tk);

        // Tạo mã và gửi mail xác thực
        String code = emailService.createCode(email);
        emailService.sendVerificationCode(email, code);

        return tk;
    }

    // Đăng nhập
    @Override
    public TKDoiTuongSuDung login(String email, String password) {
        Optional<TKDoiTuongSuDung> opt = tkDoiTuongSuDungRepository.findByTenDangNhap(email);
        if (opt.isEmpty()) throw new RuntimeException("Account not found!");

        TKDoiTuongSuDung tk = opt.get();
        if (!tk.isTrangThaiKichHoat()) throw new RuntimeException("Account email not verified!");

        if (!encoder.matches(password, tk.getMatKhau())) {
            throw new RuntimeException("Password incorrect!");
        }
        return tk;
    }

    // kích hoạt tài khoản sau đăng ký
    @Override
    public void activateUser(String email) {
        Optional<TKDoiTuongSuDung> opt = tkDoiTuongSuDungRepository.findByTenDangNhap(email);
        if (opt.isPresent()) {
            TKDoiTuongSuDung tk = opt.get();
            tk.setTrangThaiKichHoat(true);
            tkDoiTuongSuDungRepository.save(tk);
        } else {
            throw new RuntimeException("No account found to activate: " + email);
        }
    }

    // Quên mật khẩu -> gửi mã reset mật khẩu
    @Override
    public void sendResetPasswordCode(String email) {
        TKDoiTuongSuDung tk = tkDoiTuongSuDungRepository.findByTenDangNhap(email)
                .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));

        String code = emailService.createCode(email);
        emailService.sendResetPasswordEmail(email, code);
    }

    @Override
    public void resetPassword(String email, String code, String newPassword) {
        boolean valid = emailService.verify(email, code);
        if (!valid) throw new RuntimeException("The verification code is incorrect or has expired!");

        TKDoiTuongSuDung tk = tkDoiTuongSuDungRepository.findByTenDangNhap(email)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        tk.setMatKhau(encoder.encode(newPassword));
        tkDoiTuongSuDungRepository.save(tk);
    }

    @Override
    public boolean existsByEmail(String email) {
        return tkDoiTuongSuDungRepository.findByTenDangNhap(email).isPresent();
    }
}