package cinema.service.impl;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.RoleService;
import cinema.service.ShoppingCartService;
import cinema.service.UserService;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String DEFAULT_ROLE_NAME = Role.RoleName.USER.name();
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService,
                                     RoleService roleService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.roleService = roleService;
    }

    @Override
    public User register(String email, String password) {
        Role defaultRole = roleService.getByName(DEFAULT_ROLE_NAME);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(defaultRole));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
