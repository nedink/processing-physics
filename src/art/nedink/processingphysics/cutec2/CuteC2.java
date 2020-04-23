package art.nedink.processingphysics.cutec2;

import art.nedink.processingphysics.CircleBody;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public final class CuteC2 {

    // adjust these primitives as seen fit
    static float c2Sin(float radians) {
        return (float) sin(radians);
    }
    static float c2Cos(float radians) {
        return (float) cos(radians);
    }
    static float c2Sqrt(float a) {
        return (float) sqrt(a);
    }
    static float c2Min(float a, float b) {
        return a < b ? a : b;
    }
    static float c2Max(float a, float b) {
        return a > b ? a : b;
    }
    static float c2Abs(float a) {
        return a < 0 ? -a : a;
    }
    static float c2Clamp(float a, float lo, float hi) {
        return c2Max(lo, c2Min(a, hi));
    }
    static C2r c2SinCos(float radians) {
        C2r rotation = new C2r();
        rotation.c = c2Cos(radians);
        rotation.s = c2Sin(radians);
        return rotation;
    }
    static float c2Sign(float a) {
        return a < 0 ? -1.0f : 1.0f;
    }

    // The rest of the functions in the header-only portion are all for internal use
    // and use the author's personal naming conventions. It is recommended to use one's
    // own math library instead of the one embedded here in cute_c2, but for those
    // curious or interested in trying it out here's the details:

    // The Mul functions are used to perform multiplication. x stands for transform,
    // v stands for vector, s stands for scalar, r stands for rotation, h stands for
    // halfspace and T stands for transpose.For example c2MulxvT stands for "multiply
    // a transform with a vector, and transpose the transform".

    // vector ops
    static C2v c2V(float x, float y) { C2v a = new C2v();a.x = x;a.y = y; return a; }
    static C2r c2R(float c, float s) { C2r r = new C2r();r.c = c;r.s = s; return r; }
    static C2v c2Add(C2v a, C2v b) { return c2V(a.x + b.x, a.y + b.y); }
    static C2v c2Sub(C2v a, C2v b) { return c2V(a.x - b.x, a.y - b.y); }
    static float c2Dot(C2v a, C2v b) { return a.x * b.x + a.y * b.y; }
    static C2v c2Mulvs(C2v a, float b) { return c2V(a.x * b, a.y * b); }
    static C2v c2Mulvv(C2v a, C2v b) { return c2V(a.x * b.x, a.y * b.y); }
    static C2v c2Div(C2v a, float b) { return c2Mulvs(a, 1.0f / b); }
    static C2v c2Skew(C2v a) { return c2V(-a.y, a.x); }
    static C2v c2CCW90(C2v a) { return c2V(a.y, -a.x); }
    static float c2Det2(C2v a, C2v b) { return a.x * b.y - a.y * b.x; }
    static C2v c2Minv(C2v a, C2v b) { return c2V(c2Min(a.x, b.x), c2Min(a.y, b.y)); }
    static C2v c2Maxv(C2v a, C2v b) { return c2V(c2Max(a.x, b.x), c2Max(a.y, b.y)); }
    static C2v c2Clampv(C2v a, C2v lo, C2v hi) { return c2Maxv(lo, c2Minv(a, hi)); }
    static C2v c2Absv(C2v a) { return c2V(c2Abs(a.x), c2Abs(a.y)); }
    static float c2Hmin(C2v a) { return c2Min(a.x, a.y); }
    static float c2Hmax(C2v a) { return c2Max(a.x, a.y); }
    static float c2Len(C2v a) { return c2Sqrt(c2Dot(a, a)); }
    static C2v c2Norm(C2v a) { return c2Div(a, c2Len(a)); }
    static C2v c2SafeNorm(C2v a) { float sq = c2Dot(a, a); return sq != 0 ? c2Div(a, c2Len(a)) : c2V(0, 0); }
    static C2v c2Neg(C2v a) { return c2V(-a.x, -a.y); }
    static C2v c2Lerp(C2v a, C2v b, float t) { return c2Add(a, c2Mulvs(c2Sub(b, a), t)); }
    static int c2Parallel(C2v a, C2v b, float kTol)
    {
        float k = c2Len(a) / c2Len(b);
        b = c2Mulvs(b, k);
        if (c2Abs(a.x - b.x) < kTol && c2Abs(a.y - b.y) < kTol) return 1;
        return 0;
    }

    // rotation ops
    static C2r c2Rot(float radians) { return c2R(c2Cos(radians), c2Sin(radians)); }
    static C2r c2RotIdentity() { return c2R(1.0f, 0); }
    static C2v c2RotX(C2r r) { return c2V(r.c, r.s); }
    static C2v c2RotY(C2r r) { return c2V(-r.s, r.c); }
    static C2v c2Mulrv(C2r a, C2v b)  { return c2V(a.c * b.x - a.s * b.y, a.s * b.x + a.c * b.y); }
    static C2v c2MulrvT(C2r a, C2v b) { return c2V(a.c * b.x + a.s * b.y, -a.s * b.x + a.c * b.y); }
    static C2r c2Mulrr(C2r a, C2r b)  { return c2R(a.c * b.c - a.s * b.s, a.s * b.c + a.c * b.s); }
    static C2r c2MulrrT(C2r a, C2r b) { return c2R(a.c * b.c + a.s * b.s, a.c * b.s - a.s * b.c); }

    static C2v c2Mulmv(C2m a, C2v b) { return c2V(a.x.x * b.x + a.y.x * b.y, a.x.y * b.x + a.y.y * b.y); }
    static C2v c2MulmvT(C2m a, C2v b) { return c2V(a.x.x * b.x + a.x.y * b.y, a.y.x * b.x + a.y.y * b.y); }
    static C2m c2Mulmm(C2m a, C2m b)  { C2m c = new C2m();c.x = c2Mulmv(a, b.x);c.y = c2Mulmv(a, b.y); return c; }
    static C2m c2MulmmT(C2m a, C2m b) { C2m c = new C2m();c.x = c2MulmvT(a, b.x);c.y = c2MulmvT(a, b.y); return c; }

    // transform ops
    static C2x c2xIdentity() { C2x x = new C2x();x.p = c2V(0, 0);x.r = c2RotIdentity(); return x; }
    static C2v c2Mulxv(C2x a, C2v b) { return c2Add(c2Mulrv(a.r, b), a.p); }
    static C2v c2MulxvT(C2x a, C2v b) { return c2MulrvT(a.r, c2Sub(b, a.p)); }
    static C2x c2Mulxx(C2x a, C2x b) { C2x c = new C2x();c.r = c2Mulrr(a.r, b.r);c.p = c2Add(c2Mulrv(a.r, b.p), a.p); return c; }
    static C2x c2MulxxT(C2x a, C2x b) { C2x c = new C2x();c.r = c2MulrrT(a.r, b.r);c.p = c2MulrvT(a.r, c2Sub(b.p, a.p)); return c; }
    static C2x c2Transform(C2v p, float radians) { C2x x = new C2x();x.r = c2Rot(radians);x.p = p; return x; }

    // halfspace ops
    static C2v c2Origin(C2h h) { return c2Mulvs(h.n, h.d); }
    static float c2Dist(C2h h, C2v p) { return c2Dot(h.n, p) - h.d; }
    static C2v c2Project(C2h h, C2v p) { return c2Sub(p, c2Mulvs(h.n, c2Dist(h, p))); }
    static C2h c2Mulxh(C2x a, C2h b) { C2h c = new C2h();c.n = c2Mulrv(a.r, b.n);c.d = c2Dot(c2Mulxv(a, c2Origin(b)), c.n); return c; }
    static C2h c2MulxhT(C2x a, C2h b) { C2h c = new C2h();c.n = c2MulrvT(a.r, b.n);c.d = c2Dot(c2MulxvT(a, c2Origin(b)), c.n); return c; }
    static C2v c2Intersect(C2v a, C2v b, float da, float db) { return c2Add(a, c2Mulvs(c2Sub(b, a), (da / (da - db)))); }

//    void c2BBVerts(c2v* out, c2AABB* bb)
//    {
//        out[0] = bb->min;
//        out[1] = c2V(bb->max.x, bb->min.y);
//        out[2] = bb->max;
//        out[3] = c2V(bb->min.x, bb->max.y);
//    }

    public static boolean c2CircletoCircle(C2Circle a, C2Circle b)
    {
        C2v c = c2Sub(b.p, a.p);
        float d2 = c2Dot(c, c);
        float r = a.r + b.r;
        return d2 < r * r;
    }

    public static C2Manifold c2CircletoCircleManifold(C2Circle a, C2Circle b)
    {
        C2Manifold m = new C2Manifold();
        m.count = 0;
        C2v d = c2Sub(b.p, a.p);
        float d2 = c2Dot(d, d);
        float r = a.r + b.r;
        if (d2 < r * r)
        {
            float l = c2Sqrt(d2);
            C2v n = l != 0 ? c2Mulvs(d, 1.0f / l) : c2V(0, 1.0f);
            m.count = 1;
            m.depths[0] = r - l;
            m.contact_points[0] = c2Sub(b.p, c2Mulvs(n, b.r));
            m.n = n;
        }
        return m;
    }

    public static void resolveCollision(CircleBody a, CircleBody b, C2Manifold m)
    {
        // Calculate relative velocity
        C2v rv = c2Sub(b.velocity, a.velocity);

        // Calculate relative velocity in terms of the normal direction
        float velAlongNormal = c2Dot( rv, m.n );

        // Do not resolve if velocities are separating
        if(velAlongNormal > 0)
            return;

        // Calculate restitution
        float e = c2Min( a.restitution, b.restitution);

        // Calculate impulse scalar
        float j = -(1 + e) * velAlongNormal;
        j /= 1 / a.mass + 1 / b.mass;

        // Apply impulse
        C2v impulse = c2Mulvs(m.n, j);
        a.velocity.sub(c2Mulvs(impulse, 1 / a.mass));
        b.velocity.add(c2Mulvs(impulse, 1 / b.mass));
    }

}
