/*

package io.hexlet.cv.controller.account;



@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountWebinarsController {

    private final Inertia inertia;
    private final AccountWebinarService service;
    private final UserUtils userUtils;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/webinars")
    public Object index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        HttpServletRequest request) {

        var userId = userUtils.currentUserId();  // id юзера который залогинился

        var props = new HashMap<String, Object>();
        Pageable pageable = PageRequest.of(page, size);

        */


        /*
        var props = service.indexWebinars(userId.get(), pageable);

        var flash = RequestContextUtils.getInputFlashMap(request);
        if (flash != null && !flash.isEmpty()) {
            props.put("flash", flash);
        }

         */

/*

        return inertia.render("Account/Purchase/Index", props);
    }
}

 */
