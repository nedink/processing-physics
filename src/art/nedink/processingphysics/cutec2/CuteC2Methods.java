package art.nedink.processingphysics.cutec2;

public interface CuteC2Methods {

    /** position of impact p = ray.p + ray.d * raycast.t */
//    static void c2Impact(c2v ray, float t) {
//        c2Add(ray.p, c2Mulvs(ray.d, t));
//    }

    // boolean collision detection
    // these versions are faster than the manifold versions, but only give a YES/NO result
    int c2CircletoCircle(C2Circle a, C2Circle b);
    int c2CircletoAABB(C2Circle a, C2AABB b);
    int c2CircletoCapsule(C2Circle a, C2Capsule b);
    int c2AABBtoAABB(C2AABB a, C2AABB b);
    int c2AABBtoCapsule(C2AABB a, C2Capsule b);
    int c2CapsuletoCapsule(C2Capsule a, C2Capsule b);
    int c2CircletoPoly(C2Circle a, final C2Poly b, final C2x bx);
    int c2AABBtoPoly(C2AABB a, final C2Poly b, final C2x bx);
    int c2CapsuletoPoly(C2Capsule a, final C2Poly b, final C2x bx);
    int c2PolytoPoly(final C2Poly a, final C2x ax, final C2Poly b, final C2x bx);

    // ray operations
    // output is placed into the c2Raycast struct, which represents the hit location
    // of the ray. the out param contains no meaningful information if these funcs
    // return 0
    int c2RaytoCircle(C2Ray a, C2Circle b, C2Raycast out);
    int c2RaytoAABB(C2Ray a, C2AABB b, C2Raycast out);
    int c2RaytoCapsule(C2Ray a, C2Capsule b, C2Raycast out);
    int c2RaytoPoly(C2Ray a, final C2Poly b, final C2x bx_ptr, C2Raycast out);

    // manifold generation
    // these functions are (generally) slower than the boolean versions, but will compute one
    // or two points that represent the plane of contact. This information is
    // is usually needed to resolve and prevent shapes from colliding. If no coll
    // ision occured the count member of the manifold struct is set to 0.
    void c2CircletoCircleManifold(C2Circle a, C2Circle b, C2Manifold m);
    void c2CircletoAABBManifold(C2Circle a, C2AABB b, C2Manifold m);
    void c2CircletoCapsuleManifold(C2Circle a, C2Capsule b, C2Manifold m);
    void c2AABBtoAABBManifold(C2AABB a, C2AABB b, C2Manifold m);
    void c2AABBtoCapsuleManifold(C2AABB a, C2Capsule b, C2Manifold m);
    void c2CapsuletoCapsuleManifold(C2Capsule a, C2Capsule b, C2Manifold m);
    void c2CircletoPolyManifold(C2Circle a, final C2Poly b, final C2x bx, C2Manifold m);
    void c2AABBtoPolyManifold(C2AABB a, final C2Poly b, final C2x bx, C2Manifold m);
    void c2CapsuletoPolyManifold(C2Capsule a, final C2Poly b, final C2x bx, C2Manifold m);
    void c2PolytoPolyManifold(final C2Poly a, final C2x ax, final C2Poly b, final C2x bx, C2Manifold m);

    // This is an advanced function, intended to be used by people who know what they're doing.
    //
    // Runs the GJK algorithm to find closest points, returns distance between closest points.
    // outA and outB can be NULL, in this case only distance is returned. ax_ptr and bx_ptr
    // can be NULL, and represent local to world transformations for shapes a and b respectively.
    // use_radius will apply radii for capsules and circles (if set to false, spheres are
    // treated as points and capsules are treated as line segments i.e. rays). The cache parameter
    // should be NULL, as it is only for advanced usage (unless you know what you're doing, then
    // go ahead and use it). iterations is an optional parameter.
    //
    // IMPORTANT NOTE:
    // The GJK function is sensitive to large shapes, since it internally will compute signed area
    // values. `c2GJK` is called throughout cute c2 in many ways, so try to make sure all of your
    // collision shapes are not gigantic. For example, try to keep the volume of all your shapes
    // less than 100.0f. If you need large shapes, you should use tiny collision geometry for all
    // cute c2 function, and simply render the geometry larger on-screen by scaling it up.
//    float c2GJK(final void* a, C2_TYPE typeA, final c2x* ax_ptr, final void* b, C2_TYPE typeB, final c2x bx_ptr, c2v outA, c2v outB, int use_radius, int iterations, c2GJKCache cache);

    // This is an advanced function, intended to be used by people who know what they're doing.
    //
    // Computes the time of impact from shape A and shape B. The velocity of each shape is provided
    // by vA and vB respectively. The shapes are *not* allowed to rotate over time. The velocity is
    // assumed to represent the change in motion from time 0 to time 1, and so the return value will
    // be a number from 0 to 1. To move each shape to the colliding configuration, multiply vA and vB
    // each by the return value. ax_ptr and bx_ptr are optional parameters to transforms for each shape,
    // and are typically used for polygon shapes to transform from model to world space. Set these to
    // NULL to represent identity transforms. iterations is an optional parameter. use_radius
    // will apply radii for capsules and circles (if set to false, spheres are treated as points and
    // capsules are treated as line segments i.e. rays).
    //
    // IMPORTANT NOTE:
    // The c2TOI function can be used to implement a "swept character controller", but it can be
    // difficult to do so. Say we compute a time of impact with `c2TOI` and move the shapes to the
    // time of impact, and adjust the velocity by zeroing out the velocity along the surface normal.
    // If we then call `c2TOI` again, it will fail since the shapes will be considered to start in
    // a colliding configuration. There are many styles of tricks to get around this problem, and
    // all of them involve giving the next call to `c2TOI` some breathing room. It is recommended
    // to use some variation of the following algorithm:
    //
    // 1. Call c2TOI.
    // 2. Move the shapes to the TOI.
    // 3. Slightly inflate the size of one, or both, of the shapes so they will be intersecting.
    //    The purpose is to make the shapes numerically intersecting, but not visually intersecting.
    // 4. Compute the collision manifold between the inflated shapes (for example, use c2PolytoPolyManifold).
    // 5. Gently push the shapes apart. This will give the next call to c2TOI some breathing room.
//    float c2TOI(const void* A, C2_TYPE typeA, const c2x* ax_ptr, c2v vA, const void* B, C2_TYPE typeB, const c2x* bx_ptr, c2v vB, int use_radius, int* iterations);

    // Computes 2D convex hull. Will not do anything if less than two verts supplied. If
    // more than C2_MAX_POLYGON_VERTS are supplied extras are ignored.
//    int c2Hull(c2v* verts, int count);
//    void c2Norms(c2v* verts, c2v* norms, int count);

    // runs c2Hull and c2Norms, assumes p->verts and p->count are both set to valid values
//    void c2MakePoly(c2Poly* p);

    // Generic collision detection routines, useful for games that want to use some poly-
    // morphism to write more generic-styled code. Internally calls various above functions.
    // For AABBs/Circles/Capsules ax and bx are ignored. For polys ax and bx can define
    // model to world transformations, or be NULL for identity transforms.
//    int c2Collided(const void* A, const c2x* ax, C2_TYPE typeA, const void* B, const c2x* bx, C2_TYPE typeB);
//    void c2Collide(const void* A, const c2x* ax, C2_TYPE typeA, const void* B, const c2x* bx, C2_TYPE typeB, c2Manifold* m);
//    int c2CastRay(c2Ray A, const void* B, const c2x* bx, C2_TYPE typeB, c2Raycast* out);


}
