package hcmute.edu.vn.HeThongHocCodeTichHopAI.service.auth;

import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.DoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.GioiTinh;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.LoaiDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.model.TKDoiTuongSuDung;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.DoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.repository.TKDoiTuongSuDungRepository;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.IAuthService;
import hcmute.edu.vn.HeThongHocCodeTichHopAI.service.email.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public TKDoiTuongSuDung register(
            String hoTen,
            String soDienThoai,
            String ngaySinh,
            String gioiTinh,
            String email,
            String password
    ) {
        if (tkDoiTuongSuDungRepository.findByTenDangNhap(email).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
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
        doiTuong.setSoDienThoai(soDienThoai);
        doiTuong.setEmail(email);
        doiTuong.setNgaySinh(LocalDate.parse(ngaySinh).atStartOfDay());
        doiTuong.setGioiTinh(GioiTinh.valueOf(gioiTinh.toUpperCase()));
        doiTuong.setLoaiDoiTuongSuDung(LoaiDoiTuongSuDung.KHACHHANG);
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
        if (opt.isEmpty()) throw new RuntimeException("Không tìm thấy tài khoản!");

        TKDoiTuongSuDung tk = opt.get();
        if (!tk.isTrangThaiKichHoat()) throw new RuntimeException("Tài khoản chưa xác thực email!");

        if (!encoder.matches(password, tk.getMatKhau())) {
            throw new RuntimeException("Mật khẩu không đúng!");
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
            throw new RuntimeException("Không tìm thấy tài khoản để kích hoạt: " + email);
        }
    }

    // Quên mật khẩu -> gửi mã reset mật khẩu
    public void sendResetPasswordCode(String email) {
        TKDoiTuongSuDung tk = tkDoiTuongSuDungRepository.findByTenDangNhap(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với email: " + email));

        String code = emailService.createCode(email);
        emailService.sendResetPasswordEmail(email, code);
    }

    // Xác thực mã và reset mật khẩu
    public void resetPassword(String email, String code, String newPassword) {
        boolean valid = emailService.verify(email, code);
        if (!valid) throw new RuntimeException("Mã xác nhận không đúng hoặc đã hết hạn!");

        TKDoiTuongSuDung tk = tkDoiTuongSuDungRepository.findByTenDangNhap(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản!"));

        tk.setMatKhau(encoder.encode(newPassword));
        tkDoiTuongSuDungRepository.save(tk);
    }
}